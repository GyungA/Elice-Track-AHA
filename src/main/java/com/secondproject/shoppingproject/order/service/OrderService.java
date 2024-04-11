package com.secondproject.shoppingproject.order.service;

import com.secondproject.shoppingproject.order.dto.order.user.*;
import com.secondproject.shoppingproject.order.dto.orderDetail.OrderDetailCountAndProductNamesDto;
import com.secondproject.shoppingproject.order.entity.Order;
import com.secondproject.shoppingproject.order.entity.OrderDetail;
import com.secondproject.shoppingproject.order.exception.*;
import com.secondproject.shoppingproject.order.repository.OrderRepository;
import com.secondproject.shoppingproject.order.status.OrderStatus;
import com.secondproject.shoppingproject.user.entity.User;
import com.secondproject.shoppingproject.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderDetailService orderDetailService;

    public List<OrderHistoryResponseDto> getMyOrder(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 user id를 찾을 수 없습니다."));
        List<Order> orders = orderRepository.findByUserOrderByCreatedAtDesc(user);

        return orders.stream()
                .map(order -> {
                    OrderDetailCountAndProductNamesDto dto = orderDetailService.getOrderDetailCountAndProductNames(order);
                    return new OrderHistoryResponseDto(order, dto);
                })
                .collect(Collectors.toList());
    }

    public OrderDetailHistoryResponseDto getDetailOrder(Long userId, Long orderId, boolean isSeller) {
        //user가 가지고 있는 order들 중, 해당 orderId를 가진 객체 가져오기
        if(isSeller){
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new EntityNotFoundException("해당하는 order id를 찾을 수 없습니다."));
        return new OrderDetailHistoryResponseDto(order, orderDetailService.getOrderDetailListBySeller(order, userId));
        } else{
            Order order = orderRepository.findByUserIdAndOrderId(userId, orderId)
                    .orElseThrow(() -> new EntityNotFoundException("해당하는 user id 또는 order id를 찾을 수 없습니다."));

            return new OrderDetailHistoryResponseDto(order, orderDetailService.getOrderDetailList(order));
        }
    }

    public OrderPayResponseDto getPayInfo(Long userId, Long orderId) {
        //TODO: 본인확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 user id를 찾을 수 없습니다."));
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 order id를 찾을 수 없습니다."));

        if (userId == order.getUser().getUser_id()) {
            DecimalFormat decimalFormat = new DecimalFormat("#,##0");
            String formattedPayment = decimalFormat.format(order.getTotalPayment());

            return OrderPayResponseDto.builder()
                    .name(user.getName())
                    .phone(user.getPhone())
                    .email(user.getEmail())
                    .grade(user.getGrade())
                    .address(user.getAddress())

                    .orderDetailInfoDtos(orderDetailService.getOrderDetailList(order))
                    .totalPayment(formattedPayment)
                    .build();
        }
        throw new AccessDeniedException("해당 주문을 확인할 권한이 없습니다.");
    }

    @Transactional
    public OrderDetailHistoryResponseDto payProduct(PayProductRequestDto requestDto) {
        Order order = orderRepository.findById(requestDto.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("해당하는 order id를 찾을 수 없습니다."));
        boolean isSamePerson = requestDto.getUserId() == order.getUser().getUser_id();
        boolean isOrderPendingStatus = orderDetailService.isAllStatus(order, OrderStatus.ORDER_PENDING);
        if (isSamePerson && isOrderPendingStatus) {
            orderDetailService.setAllOrderStatus(order, OrderStatus.ORDER_COMPLETE);
            order.setDeliveryAddress(requestDto.getDeliveryAddress());
            order.setReceiverName(requestDto.getReceiverName());
            order.setReceiverPhoneNumber(requestDto.getReceiverPhoneNumber());
            order = orderRepository.save(order);
        } else if (!isSamePerson) {
            throw new AccessDeniedException("해당 주문을 결제할 권한이 없습니다.");
        } else if (!isOrderPendingStatus) {
            throw new AlreadyOrderedException();
        }

        return new OrderDetailHistoryResponseDto(order, orderDetailService.getOrderDetailList(order));
    }

    @Transactional
    public Long buyProduct(BuyProductRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("해당하는 user id를 찾을 수 없습니다."));

        List<Long> productIds = requestDto.getProductIds();
        List<Integer> amounts = requestDto.getAmounts();

        Order order = Order.builder()
                .user(user)
//                .totalPayment(calculateTotalPayment(orderDetailService.getProductPrice(productIds), amounts))
                .totalPayment(orderDetailService.calculateTotalPayment(productIds, amounts))
                .build();

        order = orderRepository.save(order);

        List<OrderDetail> orderDetails = new ArrayList<>();
        for (int i = 0; i < productIds.size(); i++) {
            Long productId = productIds.get(i);
            Integer amount = amounts.get(i);

            orderDetails.add(orderDetailService.save(productId, amount, order)); //모든 상품 상태는 pending
        }
        return order.getId();
    }

    private int calculateTotalPayment(List<Integer> productPayments, List<Integer> amounts) {
        if (productPayments.size() == amounts.size()) {
            int sum = 0;
            for (int i = 0; i < productPayments.size(); i++) {
                sum += productPayments.get(i) * amounts.get(i);
            }
            return sum;
        }
        throw new InvalidRequestDataException("상품 id와 상품 수량의 개수가 맞지 않습니다.");
    }

    @Transactional
    public OrderDetailHistoryResponseDto update(OrderUpdateRequestDto requestDto) {
        Order order = orderRepository.findById(requestDto.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("해당하는 order id를 찾을 수 없습니다."));

        if ((order.getUser().getUser_id() == requestDto.getUserId())) {
            if (orderDetailService.isAllStatus(order, OrderStatus.ORDER_COMPLETE)) {
                String address = requestDto.getDeliveryAddress();
                String name = requestDto.getReceiverName();
                String phone = requestDto.getReceiverPhoneNumber();

                if (address != null && !address.isBlank()) {
                    order.setDeliveryAddress(requestDto.getDeliveryAddress());
                }
                if (name != null && !name.isBlank()) {
                    order.setReceiverName(requestDto.getReceiverName());
                }
                if (phone != null && !phone.isBlank()) {
                    order.setReceiverPhoneNumber(requestDto.getReceiverPhoneNumber());
                }

                order = orderRepository.save(order);
                return new OrderDetailHistoryResponseDto(order, orderDetailService.getOrderDetailList(order));
            } else {
                log.warn("이미 상품이 배송 중이거나 도착하였기 때문에 정보 수정 불가");
                throw new OrderModificationDeniedException("정보 수정");
            }
        } else {
            String message = requestDto.getOrderId() + "번 주문을 수정할 권한이 없습니다.";
            log.warn(message);
            throw new AccessDeniedException(message);
        }
    }

    @Transactional
    public OrderDetailHistoryResponseDto cancel(OrderCancelRequestDto requestDto) {
        Order order = orderRepository.findById(requestDto.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("해당하는 order id를 찾을 수 없습니다."));

        if (order.getUser().getUser_id() == requestDto.getUserId()) {
            if (orderDetailService.isAllStatus(order, OrderStatus.ORDER_COMPLETE)) {
                orderDetailService.setAllOrderStatus(order, OrderStatus.CANCELLATION_COMPLETE);
//                order.setOrderStatus(OrderStatus.CANCELLATION_COMPLETE);
                order = orderRepository.save(order);
                return new OrderDetailHistoryResponseDto(order, orderDetailService.getOrderDetailList(order));
            }

            log.warn("이미 상품이 배송 중이거나 도착하였기 때문에 취소 불가");
            throw new OrderModificationDeniedException("취소");
        }

        String message = requestDto.getOrderId() + "번 주문을 취소할 권한이 없습니다.";
        log.warn(message);
        throw new AccessDeniedException(message);
    }
}

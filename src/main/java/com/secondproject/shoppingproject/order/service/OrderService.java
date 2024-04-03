package com.secondproject.shoppingproject.order.service;

import com.secondproject.shoppingproject.order.dto.order.admin.AdminOrderDeleteRequestDto;
import com.secondproject.shoppingproject.order.dto.order.admin.AdminOrderUpdateRequestDto;
import com.secondproject.shoppingproject.order.dto.order.user.*;
import com.secondproject.shoppingproject.order.dto.orderDetail.OrderDetailCountAndProductNamesDto;
import com.secondproject.shoppingproject.order.entity.Order;
import com.secondproject.shoppingproject.order.entity.OrderDetail;
import com.secondproject.shoppingproject.order.exception.AccessDeniedException;
import com.secondproject.shoppingproject.order.exception.EntityNotFoundException;
import com.secondproject.shoppingproject.order.exception.InvalidRequestDataException;
import com.secondproject.shoppingproject.order.exception.OrderModificationDeniedException;
import com.secondproject.shoppingproject.order.repository.OrderRepository;
import com.secondproject.shoppingproject.order.status.OrderStatus;
import com.secondproject.shoppingproject.user.constant.Role;
import com.secondproject.shoppingproject.user.entity.User;
import com.secondproject.shoppingproject.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    public OrderDetailHistoryResponseDto getDetailOrder(Long userId, Long orderId) {
        //user가 가지고 있는 order들 중, 해당 orderId를 가진 객체 가져오기
        Order order = orderRepository.findByUserIdAndOrderId(userId, orderId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 user id 또는 order id를 찾을 수 없습니다."));

        return new OrderDetailHistoryResponseDto(order, orderDetailService.getOrderDetailList(order));
    }

    public OrderPayResponseDto getPayInfo(Long userId, Long orderId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 user id를 찾을 수 없습니다."));
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 order id를 찾을 수 없습니다."));

        return OrderPayResponseDto.builder()
                .name(user.getName())
                .phone(user.getPhone())
                .email(user.getEmail())
                .grade(user.getGrade())
                .address(user.getAddress())

                .orderDetailInfoDtos(orderDetailService.getOrderDetailList(order))
                .totalPayment(order.getTotalPayment())
                .build();
    }

//    public OrderDetailHistoryResponseDto payProduct(PayProductRequestDto requestDto){
//
//    }

    @Transactional
    public Long buyProduct(BuyProductRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("해당하는 user id를 찾을 수 없습니다."));

        List<Long> productIds = requestDto.getProductIds();
        List<Integer> amounts = requestDto.getAmounts();

        Order order = Order.builder()
                .user(user)
                .orderStatus(OrderStatus.ORDER_PENDING)
                .totalPayment(calculateTotalPayment(orderDetailService.getProductPrice(productIds), amounts))
                .build();

        order = orderRepository.save(order);

        List<OrderDetail> orderDetails = new ArrayList<>();
        for (int i = 0; i < productIds.size(); i++) {
            Long productId = productIds.get(i);
            Integer amount = amounts.get(i);

            orderDetails.add(orderDetailService.save(productId, amount, order));
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
            if (order.getOrderStatus() == OrderStatus.ORDER_COMPLETE) {

                if (!requestDto.getDeliveryAddress().isBlank()) {
                    order.setDeliveryAddress(requestDto.getDeliveryAddress());
                }
                if (!requestDto.getReceiverName().isBlank()) {
                    order.setReceiverName(requestDto.getReceiverName());
                }
                if (!requestDto.getReceiverPhoneNumber().isBlank()) {
                    order.setReceiverPhoneNumber(requestDto.getReceiverPhoneNumber());
                }

                order = orderRepository.save(order);
                return new OrderDetailHistoryResponseDto(order, orderDetailService.getOrderDetailList(order));
            } else {
                log.warn("이미 상품이 배송 중이거나 도착하였기 때문에 정보 수정 불가");
                throw new OrderModificationDeniedException();
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

        if (order.getUser().getUser_id() == requestDto.getUserId() && (order.getOrderStatus() == OrderStatus.ORDER_COMPLETE)) {
            order.setOrderStatus(OrderStatus.REQUEST_CANCELLATION);
            order = orderRepository.save(order);
            return new OrderDetailHistoryResponseDto(order, orderDetailService.getOrderDetailList(order));
        } else {
            String message = requestDto.getOrderId() + "번 주문을 취소할 권한이 없습니다.";
            log.warn(message);
            throw new AccessDeniedException(message);
        }
    }


    /*
    admin
     */

    public List<OrderHistoryResponseDto> getOrderHistory(Long userId, Long sellerId, Long buyerId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 user id를 찾을 수 없습니다."));
        if (user.getRole() == Role.ADMIN) { //시큐리티 구현하면 없애기?

            List<Order> orders = null;
            if (sellerId != null && buyerId != null) {

            } else if (sellerId != null) {

            } else if (buyerId != null) {

            } else {
                //결제일 최신순으로 정렬
                orders = orderRepository.findAllByOrderByCreatedAtDesc();
            }
            return orders.stream()
                    .map(order -> {
                        OrderDetailCountAndProductNamesDto dto = orderDetailService.getOrderDetailCountAndProductNames(order);
                        return new OrderHistoryResponseDto(order, dto);
                    })
                    .collect(Collectors.toList());
        }

        throw new AccessDeniedException("관리자 권한이 없으므로 조회 불가합니다.");
    }


    @Transactional
    public OrderDetailHistoryResponseDto update(AdminOrderUpdateRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("해당하는 user id를 찾을 수 없습니다."));
        if (user.getRole() == Role.ADMIN) { //시큐리티 구현하면 없애기?
            Order order = orderRepository.findById(requestDto.getOrderId())
                    .orElseThrow(() -> new EntityNotFoundException("해당하는 order id를 찾을 수 없습니다."));
            order.setOrderStatus(requestDto.getOrderStatus());
            order = orderRepository.save(order);
            return new OrderDetailHistoryResponseDto(order, orderDetailService.getOrderDetailList(order));
        } else {
            throw new AccessDeniedException("관리자 권한이 없으므로 조회 불가합니다.");
        }
    }

    public OrderDetailHistoryResponseDto delete(AdminOrderDeleteRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("해당하는 user id를 찾을 수 없습니다."));
        if (user.getRole() == Role.ADMIN) { //시큐리티 구현하면 없애기?
            Order order = orderRepository.findById(requestDto.getOrderId())
                    .orElseThrow(() -> new EntityNotFoundException("해당하는 order id를 찾을 수 없습니다."));
            order.setOrderStatus(OrderStatus.DELETE);
            order = orderRepository.save(order);
            return new OrderDetailHistoryResponseDto(order, orderDetailService.getOrderDetailList(order));
        } else {
            throw new AccessDeniedException("관리자 권한이 없으므로 조회 불가합니다.");
        }
    }

    @Transactional
    public OrderDetailHistoryResponseDto adminCancel(OrderCancelRequestDto requestDto) {
        Order order = orderRepository.findById(requestDto.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("해당하는 order id를 찾을 수 없습니다."));

        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("해당하는 user id를 찾을 수 없습니다."));

        if (user.getRole() == Role.ADMIN) {
            order.setOrderStatus(OrderStatus.CANCELLATION_COMPLETE);
            order = orderRepository.save(order);
            return new OrderDetailHistoryResponseDto(order, orderDetailService.getOrderDetailList(order));
        } else {
            String message = "관리자 권한이 없으므로 취소 불가합니다.";
            log.warn(message);
            throw new AccessDeniedException(message);
        }
    }
}

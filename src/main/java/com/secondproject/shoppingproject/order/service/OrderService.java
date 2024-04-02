package com.secondproject.shoppingproject.order.service;

import com.secondproject.shoppingproject.order.dto.order.admin.AdminOrderDeleteRequestDto;
import com.secondproject.shoppingproject.order.dto.order.admin.AdminOrderUpdateRequestDto;
import com.secondproject.shoppingproject.order.dto.order.user.*;
import com.secondproject.shoppingproject.order.dto.orderDetail.OrderDetailCountAndProductNamesDto;
import com.secondproject.shoppingproject.order.entity.Order;
import com.secondproject.shoppingproject.order.entity.OrderDetail;
import com.secondproject.shoppingproject.order.exception.AccessDeniedException;
import com.secondproject.shoppingproject.order.exception.EntityNotFoundException;
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

//    public OrderDetailHistoryResponseDto orderProductInCart(Long userId) {
//        //userId에 해당하는 장바구니 목록 가져오기
//        // 장바구니에 담겨있는 상품 정보를
//    }

    @Transactional
    public OrderDetailHistoryResponseDto orderProductInstantly(OrderInstantRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("해당하는 user id를 찾을 수 없습니다."));

        Order order = Order.builder()
                .user(user)
                .orderStatus(OrderStatus.ORDER_COMPLETE)
                .deliveryAddress(requestDto.getDeliveryAddress())
                .receiverName(requestDto.getReceiverName())
                .receiverPhoneNumber(requestDto.getReceiverPhoneNumber())
                .build();

        OrderDetail orderDetail = orderDetailService.save(requestDto.getProductId(), requestDto.getAmount(), order);
        order.setTotalPayment(orderDetail.getPayment());
        order = orderRepository.save(order);

        return new OrderDetailHistoryResponseDto(order, orderDetailService.getOrderDetailList(order));
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

        if ((order.getUser().getUser_id() == requestDto.getUserId())) {
            order.setOrderStatus(OrderStatus.REQUEST_CANCELLATION);
            order = orderRepository.save(order);
            return new OrderDetailHistoryResponseDto(order, orderDetailService.getOrderDetailList(order));
        } else {
            String message = requestDto.getOrderId() + "번 주문을 취소할 권한이 없습니다.";
            log.warn(message);
            throw new AccessDeniedException(message);
        }
    }

    public List<OrderHistoryResponseDto> getMyOrderHistory(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 user id를 찾을 수 없습니다."));
        if (user.getRole() == Role.ADMIN) { //시큐리티 구현하면 없애기?
            //결제일 최신순으로 정렬
            List<Order> orders = orderRepository.findAll();
            return orders.stream()
                    .map(order -> {
                        OrderDetailCountAndProductNamesDto dto = orderDetailService.getOrderDetailCountAndProductNames(order);
                        return new OrderHistoryResponseDto(order, dto);
                    })
                    .collect(Collectors.toList());
        } else {
            throw new AccessDeniedException("관리자 권한이 없으므로 조회 불가합니다.");
        }
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
}

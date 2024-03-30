package com.secondproject.shoppingproject.order.service;

import com.secondproject.shoppingproject.order.dto.orderDetail.OrderDetailCountAndProductNamesDto;
import com.secondproject.shoppingproject.order.dto.order.user.OrderDetailHistoryResponseDto;
import com.secondproject.shoppingproject.order.dto.order.user.OrderHistoryResponseDto;
import com.secondproject.shoppingproject.order.dto.order.user.OrderInstantRequestDto;
import com.secondproject.shoppingproject.order.entity.Order;
import com.secondproject.shoppingproject.order.entity.OrderDetail;
import com.secondproject.shoppingproject.order.repository.OrderRepository;
import com.secondproject.shoppingproject.order.status.OrderStatus;
import com.secondproject.shoppingproject.user.entity.User;
import com.secondproject.shoppingproject.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderDetailService orderDetailService;

    public List<OrderHistoryResponseDto> getMyOrder(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("해당하는 user id를 찾을 수 없습니다."));
        List<Order> orders = orderRepository.findByUser(user);

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
                .orElseThrow(() -> new NoSuchElementException("해당하는 user id 또는 order id를 찾을 수 없습니다."));

        return new OrderDetailHistoryResponseDto(order, orderDetailService.getOrderDetailList(order));
    }

//    public OrderDetailHistoryResponseDto orderProductInCart(Long userId) {
//        //userId에 해당하는 장바구니 목록 가져오기
//        //
//    }

    @Transactional
    public OrderDetailHistoryResponseDto orderProductInstantly(OrderInstantRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new NoSuchElementException("해당하는 user id를 찾을 수 없습니다."));

        Order order = Order.builder()
                .user(user)
                .orderStatus(OrderStatus.ORDER_COMPLETE)
                .deliveryAddress(requestDto.getDeliveryAddress())
                .receiverName(requestDto.getReceiverName())
                .receiverPhoneNumber(requestDto.getReceiverPhoneNumber())
                .build();

        OrderDetail orderDetail = orderDetailService.save(requestDto.getProductId(), requestDto.getQuantity(), order);
        order.setTotalPayment(orderDetail.getPayment());
        order = orderRepository.save(order);

        return new OrderDetailHistoryResponseDto(order, orderDetailService.getOrderDetailList(order));
    }

//    public OrderDetailHistoryResponseDto update(OrderUpdateRequestDto requestDto) {
//
//    }
//
//    public OrderDetailHistoryResponseDto cancel(OrderCancelRequestDto requestDto) {
//
//    }
}

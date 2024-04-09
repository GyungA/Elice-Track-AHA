package com.secondproject.shoppingproject.order.service;

import com.secondproject.shoppingproject.order.dto.order.admin.AdminOrderUpdateRequestDto;
import com.secondproject.shoppingproject.order.dto.order.user.OrderCancelRequestDto;
import com.secondproject.shoppingproject.order.dto.order.user.OrderDetailHistoryResponseDto;
import com.secondproject.shoppingproject.order.dto.order.user.OrderHistoryResponseDto;
import com.secondproject.shoppingproject.order.dto.orderDetail.OrderDetailCountAndProductNamesDto;
import com.secondproject.shoppingproject.order.entity.Order;
import com.secondproject.shoppingproject.order.entity.OrderDetail;
import com.secondproject.shoppingproject.order.exception.AccessDeniedException;
import com.secondproject.shoppingproject.order.exception.EntityNotFoundException;
import com.secondproject.shoppingproject.order.repository.OrderDetailRepository;
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
public class AdminOrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderDetailService orderDetailService;


    public List<OrderHistoryResponseDto> getOrderHistory(Long userId, Long buyerId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 user id를 찾을 수 없습니다."));
        if (user.getRole() == Role.ADMIN) { //시큐리티 구현하면 없애기?
            List<Order> orders = null;
            if (buyerId != null) {
                //user id가 파는 상품 중, buyer id가 구매한 주문 내역
                orders = orderRepository.findByBuyerIdAndSellerIdOrderByCreatedAtDesc(buyerId, userId);
            } else {
                //user id가 파는 상품의 모든 주문 내역
                //결제일 최신순으로 정렬
                orders = orderRepository.findAllBySellerIdOrderByCreatedAtDesc(userId);
            }
            return orders.stream()
                    .map(order -> {
                        OrderDetailCountAndProductNamesDto dto = orderDetailService.getOrderDetailCountAndProductNamesBySeller(order, userId);
                        return new OrderHistoryResponseDto(order, dto);
                    })
                    .collect(Collectors.toList());
        }

        throw new AccessDeniedException("판매자 권한이 없으므로 조회 불가합니다.");
    }


    @Transactional
    public OrderDetailHistoryResponseDto update(AdminOrderUpdateRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("해당하는 user id를 찾을 수 없습니다."));
        if (user.getRole() == Role.ADMIN) { //시큐리티 구현하면 없애기?;

            //order의 모든 상품에 대한 상태를 바꾸지 말고 특정 판매자가 판매하는 상품에 대해서만 상태 변경
            orderDetailService.setOrderStatus(requestDto.getOrderDetailId(), requestDto.getOrderStatus(), requestDto.getUserId());
//            order = orderRepository.save(order);
            Order order = orderRepository.findById(requestDto.getOrderId())
                    .orElseThrow(() -> new EntityNotFoundException("해당하는 order id를 찾을 수 없습니다."));
            return new OrderDetailHistoryResponseDto(order, orderDetailService.getOrderDetailList(order));
        } else {
            throw new AccessDeniedException("판매자 권한이 없으므로 조회 불가합니다.");
        }
    }

    @Transactional
    public OrderDetailHistoryResponseDto adminCancel(OrderCancelRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("해당하는 user id를 찾을 수 없습니다."));

        if (user.getRole() == Role.ADMIN) {
            orderDetailService.setOrderStatus(requestDto.getOrderDetailId(), OrderStatus.CANCELLATION_COMPLETE, requestDto.getUserId());

            Order order = orderRepository.findById(requestDto.getOrderId())
                    .orElseThrow(() -> new EntityNotFoundException("해당하는 order id를 찾을 수 없습니다."));
            return new OrderDetailHistoryResponseDto(order, orderDetailService.getOrderDetailList(order));
        } else {
            String message = "관리자 권한이 없으므로 취소 불가합니다.";
            log.warn(message);
            throw new AccessDeniedException(message);
        }
    }
}

package com.secondproject.shoppingproject.order.service;

import com.secondproject.shoppingproject.order.dto.order.user.OrderCancelRequestDto;
import com.secondproject.shoppingproject.order.dto.order.user.OrderDetailHistoryResponseDto;
import com.secondproject.shoppingproject.order.dto.order.user.OrderHistoryResponseDto;
import com.secondproject.shoppingproject.order.dto.order.user.OrderUpdateRequestDto;
import com.secondproject.shoppingproject.order.dto.orderDetail.OrderDetailCountAndProductNamesDto;
import com.secondproject.shoppingproject.order.dto.orderDetail.OrderDetailInfoDto;
import com.secondproject.shoppingproject.order.entity.Order;
import com.secondproject.shoppingproject.order.entity.OrderDetail;
import com.secondproject.shoppingproject.order.exception.AccessDeniedException;
import com.secondproject.shoppingproject.order.exception.EntityNotFoundException;
import com.secondproject.shoppingproject.order.exception.OrderModificationDeniedException;
import com.secondproject.shoppingproject.order.repository.OrderRepository;
import com.secondproject.shoppingproject.order.status.OrderStatus;
import com.secondproject.shoppingproject.user.constant.Grade;
import com.secondproject.shoppingproject.user.constant.Role;
import com.secondproject.shoppingproject.user.entity.User;
import com.secondproject.shoppingproject.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderDetailService orderDetailService;

    private User user;
    private Order order;

    @BeforeEach
    public void setUp() {
        user = new User(1L, "email", "password", "name", "0209244",
                true, "address", "phone", Grade.BRONZE, Role.USER);

        order = new Order();
        order.setId(1L);
        order.setUser(user);
        //order.setCreatedAt(LocalDateTime.of(2024, 4, 6, 0, 0));
    }

    @DisplayName("자신의 주문 목록 내역을 가져온다.")
    @Test
    void get_My_Order_List() {
        // given
        OrderDetailCountAndProductNamesDto dto = OrderDetailCountAndProductNamesDto.builder()
                .name("product name")
                .image("image url")
                .count(2)
                .build();

        int pageNumber = 0; // 페이지 번호 (0부터 시작)
        int pageSize = 10; // 페이지 크기

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Order> orders = List.of(order);
        Page<Order> page = new PageImpl<>(orders, pageable, orders.size());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(orderRepository.findByUserOrderByCreatedAtDesc(user, pageable)).thenReturn(page);
        when(orderDetailService.getOrderDetailCountAndProductNames(order)).thenReturn(dto);

        // when
        Page<OrderHistoryResponseDto> orderHistory = orderService.getMyOrder(1L, pageable);

        List<OrderHistoryResponseDto> orderHistoryResponseDtos = orderHistory.getContent();
        // then
        assertEquals(1, orderHistoryResponseDtos.size());
        assertEquals(order.getId(), orderHistoryResponseDtos.get(0).getOrderId());

        assertEquals(dto.getName(), orderHistoryResponseDtos.get(0).getProductName());
        assertEquals(dto.getImage(), orderHistoryResponseDtos.get(0).getProductImage());
        assertEquals(dto.getCount(), orderHistoryResponseDtos.get(0).getTotalProductCount());

        verify(userRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).findByUserOrderByCreatedAtDesc(user, pageable);
        verify(orderDetailService, times(1)).getOrderDetailCountAndProductNames(order);
    }

    @DisplayName("자신의 주문 목록 내역 조회할 때, user id가 db에 없는 경우")
    @Test
    public void get_My_Order_List_UserNotFound() {
        // given
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        int pageNumber = 0; // 페이지 번호 (0부터 시작)
        int pageSize = 10; // 페이지 크기

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        // when //then
        assertThrows(EntityNotFoundException.class, () -> orderService.getMyOrder(2L, pageable));
    }

    @DisplayName("자신의 상세 주문 내역을 가져온다.")
    @Test
    void get_My_Order_Detail() {
        // given
        OrderDetailInfoDto orderDetailInfoDto = new OrderDetailInfoDto("name", "payment", 1000, OrderStatus.ORDER_COMPLETE);
        List<OrderDetailInfoDto> orderDetailInfoDtos = new ArrayList<>();
        orderDetailInfoDtos.add(orderDetailInfoDto);

        when(orderRepository.findByUserIdAndOrderId(1L, 1L)).thenReturn(Optional.of(order));
        when(orderDetailService.getOrderDetailList(order)).thenReturn(orderDetailInfoDtos);

        // when
        OrderDetailHistoryResponseDto resultDto = orderService.getDetailOrder(1L, 1L, false);

        //then
        assertEquals(orderDetailInfoDtos, resultDto.getOrderDetailInfoDtos());

        verify(orderRepository, times(1)).findByUserIdAndOrderId(1L, 1L);
        verify(orderDetailService, times(1)).getOrderDetailList(order);
    }

    @DisplayName("자신의 상세 주문 내역 조회시, 해당 user id의 주문 중 해당 order id가 없는 경우")
    @Test
    void get_My_Order_Detail_UserNotFound() {
        // given
        when(orderRepository.findByUserIdAndOrderId(1L, 1L)).thenReturn(Optional.empty());

        // when //then
        assertThrows(EntityNotFoundException.class, () -> orderService.getDetailOrder(1L, 1L, false));
    }

    @DisplayName("배송 전까지 배송지, 수령인 이름, 수령인 연락처를 수정할 수 있다.")
    @Test
    @Transactional
    void can_modify_shipping_info_before_delivery() {
        // given
        OrderDetail orderDetail = new  OrderDetail();
        orderDetail.setOrder(order);
        orderDetailService.setAllOrderStatus(order, OrderStatus.ORDER_COMPLETE);

        OrderUpdateRequestDto requestDto = new OrderUpdateRequestDto(1L, 1L, "New Address",
                "New Receiver", "010-1234-1234");

        OrderDetailInfoDto orderDetailInfoDto = new OrderDetailInfoDto("name", "payment", 1000, OrderStatus.ORDER_COMPLETE);
        List<OrderDetailInfoDto> orderDetailInfoDtos = new ArrayList<>();
        orderDetailInfoDtos.add(orderDetailInfoDto);

        when(orderRepository.save(order)).thenReturn(order);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderDetailService.getOrderDetailList(order)).thenReturn(orderDetailInfoDtos);

        // when
        OrderDetailHistoryResponseDto resultDto = orderService.update(requestDto);

        // then
        assertEquals(order.getUser().getUser_id(), resultDto.getId());
        assertEquals(orderDetailInfoDtos, resultDto.getOrderDetailInfoDtos());

        verify(orderRepository, times(1)).findById(1L);
        verify(orderDetailService, times(1)).getOrderDetailList(order);
    }

    @DisplayName("주문을 결제한 본인이 아닌 경우")
    @Test
    @Transactional
    void can_modify_shipping_info_before_delivery_NoPermission() {
        // given
        OrderUpdateRequestDto requestDto = new OrderUpdateRequestDto(2L, 1L, "New Address",
                "New Receiver", "010-1234-1234");

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        // when //then
        assertThrows(AccessDeniedException.class, () -> orderService.update(requestDto));
    }

    @DisplayName("이미 배송을 시작한 경우")
    @Test
    @Transactional
    void can_modify_shipping_info_before_delivery_OrderAlreadyCompleted() {
        // given
        OrderDetail orderDetail = new  OrderDetail();
        orderDetail.setOrder(order);
        orderDetailService.setAllOrderStatus(order, OrderStatus.ON_DELIVERY);

        OrderUpdateRequestDto requestDto = new OrderUpdateRequestDto(1L, 1L, "New Address",
                "New Receiver", "010-1234-1234");

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        // when //then
        assertThrows(OrderModificationDeniedException.class, () -> orderService.update(requestDto));
    }

    @DisplayName("배송 전까지 주문을 취소할 수 있다.")
    @Test
    @Transactional
    void can_cancel_order_before_delivery() {
        // given
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setId(1L);

        OrderCancelRequestDto requestDto = new OrderCancelRequestDto(1L, 1L, 1L);

        OrderDetailInfoDto orderDetailInfoDto = new OrderDetailInfoDto("name", "payment", 1000, OrderStatus.ORDER_COMPLETE);
        List<OrderDetailInfoDto> orderDetailInfoDtos = new ArrayList<>();
        orderDetailInfoDtos.add(orderDetailInfoDto);

        when(orderRepository.save(order)).thenReturn(order);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderDetailService.getOrderDetailList(order)).thenReturn(orderDetailInfoDtos);

        // when
        OrderDetailHistoryResponseDto resultDto = orderService.cancel(requestDto);

        // then
        assertEquals(order.getUser().getUser_id(), resultDto.getId());
        assertEquals(orderDetailInfoDtos, resultDto.getOrderDetailInfoDtos());

        verify(orderRepository, times(1)).findById(1L);
        verify(orderDetailService, times(1)).getOrderDetailList(order);
    }
}

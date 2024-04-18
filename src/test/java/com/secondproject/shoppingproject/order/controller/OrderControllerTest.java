package com.secondproject.shoppingproject.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.secondproject.shoppingproject.order.dto.order.user.OrderCancelRequestDto;
import com.secondproject.shoppingproject.order.dto.order.user.OrderDetailHistoryResponseDto;
import com.secondproject.shoppingproject.order.dto.order.user.OrderHistoryResponseDto;
import com.secondproject.shoppingproject.order.dto.order.user.OrderUpdateRequestDto;
import com.secondproject.shoppingproject.order.dto.orderDetail.OrderDetailInfoDto;
import com.secondproject.shoppingproject.order.entity.Order;
import com.secondproject.shoppingproject.order.entity.OrderDetail;
import com.secondproject.shoppingproject.order.service.OrderService;
import com.secondproject.shoppingproject.order.status.OrderStatus;
import com.secondproject.shoppingproject.user.constant.Grade;
import com.secondproject.shoppingproject.user.constant.Role;
import com.secondproject.shoppingproject.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockBean(JpaMetamodelMappingContext.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderController.class)
@WithMockUser
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @DisplayName("[Get] 모든 주문 내역 조회")
    @Test
    public void get_My_Order_List() throws Exception {
        // given
        Long userId = 1L;
        List<OrderHistoryResponseDto> orderHistoryList = Arrays.asList(
                new OrderHistoryResponseDto(1L, 1L, "상품 이름1", "상품 이미지1",
                        5, "총 계산금액1", OrderStatus.ON_DELIVERY, "주문 날짜1"),
                new OrderHistoryResponseDto(1L, 2L, "상품 이름2", "상품 이미지2",
                        5, "총 계산금액2", OrderStatus.ORDER_COMPLETE, "주문 날짜2")
        );

        when(orderService.getMyOrder(userId)).thenReturn(orderHistoryList);

        // when
        ResultActions actions = mockMvc.perform(
                get("/orders/user/{user_id}", userId)
//                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        // then
        actions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].userId").value(1))
                .andExpect(jsonPath("$[0].orderId").value(1))
                .andExpect(jsonPath("$[1].userId").value(1))
                .andExpect(jsonPath("$[1].orderId").value(2))
                .andDo(print());
    }

    @DisplayName("[Get] 모든 주문 내역 조회 - 없는 user id")
    @Test
    public void get_My_Order_List_NotFound() throws Exception {
        // Given
        Long nonExistingUserId = 100L;

        // When & Then
        mockMvc.perform(get("/user/{user_id}", nonExistingUserId))
                .andExpect(status().isNotFound());
    }

    @DisplayName("[Get] 주문 상세 조회")
    @Test
    public void testGetDetailOrder() throws Exception {
        // Given
        Long userId = 1L;
        Long orderId = 2L;

        OrderDetailHistoryResponseDto orderDetailHistoryResponseDto = new OrderDetailHistoryResponseDto();
        orderDetailHistoryResponseDto.setId(userId);

        // Mocking the service method
        when(orderService.getDetailOrder(userId, orderId, false)).thenReturn(orderDetailHistoryResponseDto);

        // When & Then
        mockMvc.perform(
                        get("/orders/user/{user_id}/order/{order_id}", userId, orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andDo(print());
    }

    @DisplayName("[patch] 배송 전까지 배송 정보를 수정 가능합니다.")
    @Test
    public void testUpdate() throws Exception {
        // Given
        User user = new User(1L, "email", "password", "name", "0209244",
                true, "address", "phone", Grade.BRONZE, Role.USER);

        Order order = new Order();
        order.setId(123L);
        order.setUser(user);

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrder(order);
        orderDetail.setOrderStatus(OrderStatus.ORDER_COMPLETE);

        OrderUpdateRequestDto requestDto = new OrderUpdateRequestDto(1L, 123L,
                "배송지", "수령인 이름", "010-1234-1234");

        OrderDetailHistoryResponseDto orderDetailHistoryResponseDto = new OrderDetailHistoryResponseDto();
        orderDetailHistoryResponseDto.setId(requestDto.getUserId());

        when(orderService.update(requestDto)).thenReturn(orderDetailHistoryResponseDto);

        // When & Then
        mockMvc.perform(patch("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.deliveryAddress").value("배송지"))
                .andExpect(jsonPath("$.receiverName").value("수령인 이름"))
                .andExpect(jsonPath("$.receiverPhone").value("010-1234-1234"))
                .andDo(print());
    }

    @DisplayName("[patch] 배송 전까지 주문을 취소 가능합니다.")
    @Test
    public void testCancel() throws Exception {
        // Given
        User user = new User(1L, "email", "password", "name", "0209244",
                true, "address", "phone", Grade.BRONZE, Role.USER);

        Order order = new Order();
        order.setId(1L);
        order.setUser(user);

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrder(order);
        orderDetail.setOrderStatus(OrderStatus.ORDER_COMPLETE);

        OrderCancelRequestDto requestDto = new OrderCancelRequestDto(1L, 1L, 1L);

        OrderDetailHistoryResponseDto responseDto = new OrderDetailHistoryResponseDto();
        responseDto.setId(requestDto.getUserId());

        when(orderService.cancel(requestDto)).thenReturn(responseDto);

        // When //Then
        mockMvc.perform(patch("/orders/cancel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.orderStatus").value(OrderStatus.CANCELLATION_COMPLETE))
                .andDo(print());
    }
}

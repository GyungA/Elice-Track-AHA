package com.secondproject.shoppingproject.order.controller;

import com.secondproject.shoppingproject.order.dto.order.user.OrderHistoryResponseDto;
import com.secondproject.shoppingproject.order.service.OrderService;
import com.secondproject.shoppingproject.order.status.OrderStatus;
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

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockBean(JpaMetamodelMappingContext.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderController.class)
@WithMockUser
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

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
        Long nonExistingUserId = 100L; // Example of non-existing user ID

        // When & Then
        mockMvc.perform(get("/user/{user_id}", nonExistingUserId))
                .andExpect(status().isNotFound());
    }
}

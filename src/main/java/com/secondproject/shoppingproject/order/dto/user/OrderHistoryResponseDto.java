package com.secondproject.shoppingproject.order.dto.user;

import com.secondproject.shoppingproject.order.entity.OrderDetail;
import com.secondproject.shoppingproject.order.status.OrderStatus;
import lombok.Getter;

@Getter
public class OrderHistoryResponseDto {
    private Long id; //user id
    private OrderStatus orderStatus;
    private OrderDetail orderDetail;
    private String deliveryAddress;
    private String request;
    private int totalAmount;
}

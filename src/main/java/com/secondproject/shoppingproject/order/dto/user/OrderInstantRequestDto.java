package com.secondproject.shoppingproject.order.dto.user;

import com.secondproject.shoppingproject.order.entity.OrderDetail;
import lombok.Getter;

@Getter
public class OrderInstantRequestDto {
    private Long id;
    private OrderDetail orderDetail;
}

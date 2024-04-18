package com.secondproject.shoppingproject.order.dto.orderDetail;

import com.secondproject.shoppingproject.order.entity.OrderDetail;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderDetailGroupDTO {
    private List<OrderDetail> orderDetails;
}

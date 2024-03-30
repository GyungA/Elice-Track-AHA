package com.secondproject.shoppingproject.order.dto.orderDetail;

import com.secondproject.shoppingproject.order.entity.OrderDetail;
import lombok.Getter;

@Getter
public class OrderDetailInfoDto {
    private String name;
    private int payment;
    private int amount;

    public OrderDetailInfoDto(OrderDetail orderDetail){
        this.name = orderDetail.getProduct().getName();
        this.payment = orderDetail.getPayment();
        this.amount = orderDetail.getAmount();
    }
}

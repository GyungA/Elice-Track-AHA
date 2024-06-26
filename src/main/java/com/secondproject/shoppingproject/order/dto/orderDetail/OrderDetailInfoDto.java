package com.secondproject.shoppingproject.order.dto.orderDetail;

import com.secondproject.shoppingproject.order.entity.OrderDetail;
import com.secondproject.shoppingproject.order.status.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.text.DecimalFormat;

@Getter
@AllArgsConstructor
public class OrderDetailInfoDto {
    private String name;
    private String payment;
    private int amount;
    private OrderStatus orderStatus;

    public OrderDetailInfoDto(OrderDetail orderDetail) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        String formattedPayment = decimalFormat.format(orderDetail.getPayment());

        this.name = orderDetail.getProduct().getName();
        this.payment = formattedPayment;
        this.amount = orderDetail.getAmount();
        this.orderStatus = orderDetail.getOrderStatus();
    }
}

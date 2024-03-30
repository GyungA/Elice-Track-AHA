package com.secondproject.shoppingproject.order.dto.order.user;

import com.secondproject.shoppingproject.order.entity.Order;
import com.secondproject.shoppingproject.order.entity.OrderDetail;
import com.secondproject.shoppingproject.order.status.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Schema(description = "주문 상세 내역 조회할 때 response")
public class OrderDetailHistoryResponseDto {
    @Schema(description = "주문자 id")
    private Long id;

    //TODO: 굳이 OrderDetail을 리턴하지말고 dto만들자
    private List<OrderDetail> orderDetails;
    private OrderStatus orderStatus;
    private String deliveryAddress;
    private int totalPayment;
    private String orderDate;

    public OrderDetailHistoryResponseDto(Order order, List<OrderDetail> orderDetails){
        this.id = order.getUser().getUser_id();
        this.orderDetails = orderDetails;
        this.orderStatus = order.getOrderStatus();
        this.deliveryAddress = order.getDeliveryAddress();
        this.totalPayment = order.getTotalPayment();
        this.orderDate = order.getCreatedAt().toLocalDate().format(DateTimeFormatter.ofPattern("yy-MM-dd"));
    }
}

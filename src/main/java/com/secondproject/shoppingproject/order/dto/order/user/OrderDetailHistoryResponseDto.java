package com.secondproject.shoppingproject.order.dto.order.user;

import com.secondproject.shoppingproject.order.dto.orderDetail.OrderDetailInfoDto;
import com.secondproject.shoppingproject.order.entity.Order;
import com.secondproject.shoppingproject.order.status.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Schema(description = "주문 상세 내역 조회할 때 response")
public class OrderDetailHistoryResponseDto {
    @Schema(description = "주문자 id")
    private Long id;

    private List<OrderDetailInfoDto> orderDetailInfoDtos;
    private OrderStatus orderStatus;
    private String deliveryAddress;
    private String totalPayment;
    private String orderDate;

    public OrderDetailHistoryResponseDto(Order order, List<OrderDetailInfoDto> orderDetailInfoDtos){
        this.id = order.getUser().getUser_id();
        this.orderDetailInfoDtos = orderDetailInfoDtos;
        this.orderStatus = order.getOrderStatus();
        this.deliveryAddress = order.getDeliveryAddress();
        this.totalPayment = new DecimalFormat("#,##0").format(order.getTotalPayment());
        this.orderDate = order.getCreatedAt().toLocalDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }
}

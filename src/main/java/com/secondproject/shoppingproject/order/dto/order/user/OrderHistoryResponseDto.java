package com.secondproject.shoppingproject.order.dto.order.user;

import com.secondproject.shoppingproject.order.dto.orderDetail.OrderDetailCountAndProductNamesDto;
import com.secondproject.shoppingproject.order.entity.Order;
import com.secondproject.shoppingproject.order.status.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;


@Getter
@AllArgsConstructor //test
@Schema(description = "유저의 전체 주문 내역 조회할 때 response")
public class OrderHistoryResponseDto {
    private Long userId;
    private Long orderId;
    private String productName;
    private String productImage;
    private int totalProductCount;
    private String totalPayment;
    private OrderStatus orderStatus;
    private String orderDate;

    public OrderHistoryResponseDto(Order order, OrderDetailCountAndProductNamesDto orderDetailCountAndProductNamesDto){
        this.userId = order.getUser().getUser_id();
        this.orderId = order.getId();
        this.productName = orderDetailCountAndProductNamesDto.getName();
        this.productImage = orderDetailCountAndProductNamesDto.getImage();
        this.totalProductCount = orderDetailCountAndProductNamesDto.getCount();
        this.totalPayment = new DecimalFormat("#,##0").format(order.getTotalPayment());
        this.orderStatus = order.getOrderStatus();
        this.orderDate = order.getCreatedAt().toLocalDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }
}

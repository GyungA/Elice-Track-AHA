package com.secondproject.shoppingproject.order.dto.user;

import com.secondproject.shoppingproject.order.dto.OrderDetail.OrderDetailCountAndProductNamesDto;
import com.secondproject.shoppingproject.order.entity.Order;
import com.secondproject.shoppingproject.order.status.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

<<<<<<< HEAD:src/main/java/com/secondproject/shoppingproject/order/dto/order/user/OrderHistoryResponseDto.java
import java.time.format.DateTimeFormatter;
=======
import java.time.LocalDate;
>>>>>>> 94876c169f2019bac624d3003952c6e388a400a2:src/main/java/com/secondproject/shoppingproject/order/dto/user/OrderHistoryResponseDto.java

@Getter
@Schema(description = "유저의 전체 주문 내역 조회할 때 response")
public class OrderHistoryResponseDto {
    private Long userId;
    private Long orderId;
    private String productName;
    private String productImage;
    private int totalProductCount;
    private int totalPayment;
    private OrderStatus orderStatus;
    private LocalDate orderDate;

    public OrderHistoryResponseDto(Order order, OrderDetailCountAndProductNamesDto orderDetailCountAndProductNamesDto){
        this.userId = order.getUser().getUser_id();
        this.orderId = order.getId();
        this.productName = orderDetailCountAndProductNamesDto.getName();
        this.productImage = orderDetailCountAndProductNamesDto.getImage();
        this.totalProductCount = orderDetailCountAndProductNamesDto.getCount();
        this.totalPayment = order.getTotalPayment();
        this.orderStatus = order.getOrderStatus();
        this.orderDate = order.getCreatedAt().toLocalDate();
    }
}

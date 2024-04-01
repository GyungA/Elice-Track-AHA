package com.secondproject.shoppingproject.order.dto.orderDetail;

import com.secondproject.shoppingproject.order.entity.Order;
import com.secondproject.shoppingproject.order.entity.OrderDetail;
import com.secondproject.shoppingproject.order.exception.EntityNotFoundException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class OrderDetailCountAndProductNamesDto {
    @Schema(description = "주문한 상품들 중 첫번째 이름")
    private String name;

    @Schema(description = "주문한 상품들 중 첫번째 이미지", implementation = String.class)
    private String image;
    private int count;
}

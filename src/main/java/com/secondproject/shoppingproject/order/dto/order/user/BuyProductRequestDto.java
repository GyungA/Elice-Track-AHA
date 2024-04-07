package com.secondproject.shoppingproject.order.dto.order.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "구매하고 싶은 상품 목록을 결제창으로 넘기기 위한 request")
public class BuyProductRequestDto {
    private Long userId;
    private List<Long> productIds;
    private List<Integer> amounts;
}

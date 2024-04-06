package com.secondproject.shoppingproject.order.dto.order.user;

import com.secondproject.shoppingproject.order.dto.orderDetail.OrderDetailInfoDto;
import com.secondproject.shoppingproject.user.constant.Grade;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@Schema(description = "유저가 결제할 때, 결제창 구성하는 정보")
public class OrderPayResponseDto {
    //결제자 정보
    private String name;
    private String phone;
    private String email;
    private Grade grade;
    private String address;

    //상품 정보
    private List<OrderDetailInfoDto> orderDetailInfoDtos;
    private String totalPayment;
}

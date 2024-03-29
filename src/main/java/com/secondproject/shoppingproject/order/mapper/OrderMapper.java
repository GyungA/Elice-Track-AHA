package com.secondproject.shoppingproject.order.mapper;

import com.secondproject.shoppingproject.order.dto.user.OrderHistoryResponseDto;
import com.secondproject.shoppingproject.order.entity.Order;
import com.secondproject.shoppingproject.order.status.OrderStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

//@Mapper
//public interface OrderMapper {
//    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);
//    @Mapping(source = "user.id", target = "userId")
//    @Mapping(source = "id", target = "orderId")
//    public OrderHistoryResponseDto toOrderHistoryResponseDto(Order order);
//
//}

package com.secondproject.shoppingproject.order.mapper;

import com.secondproject.shoppingproject.order.dto.orderDetail.OrderDetailInfoDto;
import com.secondproject.shoppingproject.order.entity.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderDetailMapper {
    OrderDetailMapper INSTANCE = Mappers.getMapper(OrderDetailMapper.class);

//    @Mapping(source = "name", target = "product.name")
//    public OrderDetailInfoDto toOrderDetailInfoDto(OrderDetail orderDetail);
}

package com.secondproject.shoppingproject.order.service;

import com.secondproject.shoppingproject.order.dto.orderDetail.OrderDetailCountAndProductNamesDto;
import com.secondproject.shoppingproject.order.dto.orderDetail.OrderDetailInfoDto;
import com.secondproject.shoppingproject.order.entity.Order;
import com.secondproject.shoppingproject.order.entity.OrderDetail;
import com.secondproject.shoppingproject.order.exception.EntityNotFoundException;
import com.secondproject.shoppingproject.order.exception.InvalidRequestDataException;
import com.secondproject.shoppingproject.order.repository.OrderDetailRepository;
import com.secondproject.shoppingproject.order.status.OrderStatus;
import com.secondproject.shoppingproject.product.entity.Product;
import com.secondproject.shoppingproject.product.entity.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;

    @Transactional
    public OrderDetail save(Long productId, int quantity, Order order) { //유저 아이디, 상품 아이디, 수량
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 product id가 없습니다."));

        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .product(product)
                .amount(quantity)
                .payment(product.getPrice())
                .orderStatus(OrderStatus.ORDER_PENDING)
                .build();

        return orderDetailRepository.save(orderDetail);
    }

    OrderDetailCountAndProductNamesDto getOrderDetailCountAndProductNames(Order order) {
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrder(order);
        if (orderDetails == null || orderDetails.isEmpty()) {
            throw new EntityNotFoundException("해당하는 user id가 없습니다.");
        }
        return OrderDetailCountAndProductNamesDto.builder()
                .name(orderDetails.get(0).getProduct().getName())
                .image(orderDetails.get(0).getProduct().getImage())
                .count(orderDetails.size())
                .orderStatus(getMinNumberOrderStatus(orderDetails))
                .build();
    }

    OrderStatus getMinNumberOrderStatus(List<OrderDetail> orderDetails) {
        Optional<OrderStatus> minOrderStatus = orderDetails.stream()
                .map(OrderDetail::getOrderStatus)
                .min((o1, o2) -> Integer.compare(o1.getNumber(), o2.getNumber()));

        return minOrderStatus.orElseThrow(() -> new InvalidRequestDataException("지정된 주문 상태가 없습니다."));
    }

    //해당 order에 연관된 모든 product 리스트
    List<OrderDetailInfoDto> getOrderDetailList(Order order) {
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrder(order);
        return orderDetails.stream()
                .map((orderDetail -> new OrderDetailInfoDto(orderDetail)))
//                .map(orderDetail -> OrderDetailMapper.INSTANCE.toOrderDetailInfoDto(orderDetail))
                .collect(Collectors.toList());
    }

    List<Integer> getProductPrice(List<Long> productIds) {
        return productIds.stream()
                .map(productId -> productRepository.findPaymentByProductId(productId))
                .collect(Collectors.toList());
    }

    boolean isAllStatus(Order order, OrderStatus orderStatus) {
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrder(order);
        for (OrderDetail orderDetail : orderDetails) {
            if (orderDetail.getOrderStatus() != orderStatus) return false;
        }
        return true;
    }

    @Transactional
    public void setAllOrderStatus(Order order, OrderStatus orderStatus) {
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrder(order);
        for (OrderDetail orderDetail : orderDetails) {
            orderDetail.setOrderStatus(orderStatus);
            orderDetailRepository.save(orderDetail);
        }
    }

}

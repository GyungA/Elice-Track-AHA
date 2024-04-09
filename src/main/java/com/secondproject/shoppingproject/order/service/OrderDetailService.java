package com.secondproject.shoppingproject.order.service;

import com.secondproject.shoppingproject.order.dto.orderDetail.OrderDetailCountAndProductNamesDto;
import com.secondproject.shoppingproject.order.dto.orderDetail.OrderDetailInfoDto;
import com.secondproject.shoppingproject.order.entity.Order;
import com.secondproject.shoppingproject.order.entity.OrderDetail;
import com.secondproject.shoppingproject.order.exception.AccessDeniedException;
import com.secondproject.shoppingproject.order.exception.EntityNotFoundException;
import com.secondproject.shoppingproject.order.exception.InvalidRequestDataException;
import com.secondproject.shoppingproject.order.repository.OrderDetailRepository;
import com.secondproject.shoppingproject.order.status.OrderStatus;
import com.secondproject.shoppingproject.product.entity.Product;
import com.secondproject.shoppingproject.product.entity.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
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
            throw new EntityNotFoundException("해당하는 order detail이 없습니다.");
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

    @Transactional
    public void setOrderStatus(Long orderDetailId, OrderStatus orderStatus, Long sellerId) {
        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 order detail id가 없습니다."));
        if(orderDetail.getProduct().getSeller().getUser_id() == sellerId){
            orderDetail.setOrderStatus(orderStatus);
            orderDetailRepository.save(orderDetail);
            return;
        }
        throw new AccessDeniedException("해당 상품의 판매자가 아니기 때문에 권한이 없습니다.");

    }

    //user id를 받으면, 해당 user id가 판매한 상품에 대한 주문을 같은 buyer로 묶은(order detail 테이블)로 리턴
    //해당 order에 연관된 모든 product 리스트

    //이걸 조회하는 seller가 판매하는 아이템만 orderDetailInfoDto에 넣기
    List<OrderDetailInfoDto> getOrderDetailListBySeller(Order order, Long sellerId) {
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderIdAndSellerUserId(order.getId(), sellerId);
        return orderDetails.stream()
                .map((orderDetail -> new OrderDetailInfoDto(orderDetail)))
                .collect(Collectors.toList());
    }

    //이를 조회하는 seller가 판매하는 아이템만으로 다시 재구성
    OrderDetailCountAndProductNamesDto getOrderDetailCountAndProductNamesBySeller(Order order, Long sellerId) {
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderIdAndSellerUserId(order.getId(), sellerId);
        if (orderDetails == null || orderDetails.isEmpty()) {
            throw new EntityNotFoundException("해당하는 order detail이 없습니다.");
        }
        return OrderDetailCountAndProductNamesDto.builder()
                .name(orderDetails.get(0).getProduct().getName())
                .image(orderDetails.get(0).getProduct().getImage())
                .count(orderDetails.size())
                .orderStatus(getMinNumberOrderStatus(orderDetails))
                .build();
    }

}

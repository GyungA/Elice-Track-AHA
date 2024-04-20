package com.secondproject.shoppingproject.order.repository;

import com.secondproject.shoppingproject.order.entity.Order;
import com.secondproject.shoppingproject.order.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrder(Order order);

    @Query("SELECT od FROM OrderDetail od " +
            "JOIN Order o ON od.order.id = o.id AND o.id = :orderId " +
            "WHERE od.product.seller.user_id = :userId")
    List<OrderDetail> findByOrderIdAndSellerUserId(@Param("orderId") Long orderId, @Param("userId") Long userId);
}

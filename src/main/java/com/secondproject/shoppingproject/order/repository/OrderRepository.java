package com.secondproject.shoppingproject.order.repository;

import com.secondproject.shoppingproject.order.entity.Order;
import com.secondproject.shoppingproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
//    List<Order> findByUserOrderByCreatedAtDesc(User user);
    @Query("SELECT o FROM Order o " +
            "WHERE o.user = :user " +
            "ORDER BY o.createdAt DESC")
    List<Order> findByUserOrderByCreatedAtDesc(@Param("user") User user); //TODO: "주문 중" 상태 제외

//    Optional<Order> findByIdAndUserid(Long orderId, Long userId);
    @Query("SELECT o FROM Order o " +
            "WHERE o.user.user_id = :userId AND o.id = :orderId")
    Optional<Order> findByUserIdAndOrderId(@Param("userId") Long userId, @Param("orderId") Long orderId);

    List<Order> findAllByOrderByCreatedAtDesc();

    @Query("SELECT o FROM Order o " +
            "JOIN OrderDetail od ON o.id = od.order.id " +
            "WHERE od.product.seller.user_id = :sellerId " +
            "ORDER BY o.createdAt DESC ")
    List<Order> findAllBySellerIdOrderByCreatedAtDesc(@Param("sellerId") Long sellerId);

    @Query("SELECT o FROM Order o " +
            "JOIN OrderDetail od ON o.id = od.order.id " +
            "WHERE o.user.user_id = :buyerId AND od.product.seller.user_id = :sellerId " +
            "ORDER BY o.createdAt DESC ")
    List<Order> findByBuyerIdAndSellerIdOrderByCreatedAtDesc(@Param("buyerId") Long buyerId, @Param("sellerId") Long sellerId);

//    @Query("SELECT o FROM Order o " +
//            "WHERE o.user.user_id = :buyerId " +
//            "ORDER BY o.createdAt DESC")
//    List<Order> findByBuyerId(@Param("buyerId") Long buyerId);
//
//    @Query("SELECT o FROM Order o " +
//            "JOIN OrderDetail od ON o.id = od.order.id " +
//            "WHERE od.product.seller.user_id = :sellerId " +
//            "ORDER BY o.createdAt DESC")
//    List<Order> findBySellerId(@Param("sellerId") Long sellerId);
}

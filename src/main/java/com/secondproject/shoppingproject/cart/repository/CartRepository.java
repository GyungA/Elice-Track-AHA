package com.secondproject.shoppingproject.cart.repository;

import com.secondproject.shoppingproject.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("SELECT c FROM Cart c " + "WHERE c.user.user_id = :userId")
    Optional<Cart> findByUserId(@Param("userId") Long userId);
}

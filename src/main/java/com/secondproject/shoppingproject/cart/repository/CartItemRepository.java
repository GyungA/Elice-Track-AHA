package com.secondproject.shoppingproject.cart.repository;

import com.secondproject.shoppingproject.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}

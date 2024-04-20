package com.secondproject.shoppingproject.cart.service;

import com.secondproject.shoppingproject.cart.dto.CartItemDto;
import com.secondproject.shoppingproject.cart.entity.Cart;
import com.secondproject.shoppingproject.cart.entity.CartItem;
import com.secondproject.shoppingproject.cart.repository.CartItemRepository;
import com.secondproject.shoppingproject.cart.repository.CartRepository;
import com.secondproject.shoppingproject.product.entity.Product;
import com.secondproject.shoppingproject.product.entity.ProductRepository;
import com.secondproject.shoppingproject.user.entity.User;
import com.secondproject.shoppingproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartItem addCartItem(CartItemDto cartItemDto) {
        //유저, 장바구니, 상품 준비
        User currentUser = userRepository.findById(cartItemDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Optional<Cart> cartOptional = cartRepository.findByUserId(cartItemDto.getUserId());
        Product product = productRepository.findById(cartItemDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        //Optional 객체에 Cart가 있으면 꺼내오고, 없으면 새로 생성
        Cart cart;
        if (cartOptional.isPresent()) {
            cart = cartOptional.get();
        } else {
            cart = new Cart();
            cart.setUser(currentUser);
            cartRepository.save(cart);
        }

        //CartItem 생성 시작
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setCount(cartItemDto.getCount());

        return cartItemRepository.save(cartItem);
    }
}

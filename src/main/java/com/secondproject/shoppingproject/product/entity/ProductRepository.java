package com.secondproject.shoppingproject.product.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findProductById(long id);
    @Query("SELECT p.price FROM Product p WHERE p.id = :productId")
    Integer findPaymentByProductId(Long productId);
    Page<Product> findByNameContaining(String searchKeyword, Pageable pageable);

}

package com.secondproject.shoppingproject.product.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findProductById(long id);
    @Query("SELECT p.price FROM Product p WHERE p.id = :productId")
    Integer findPaymentByProductId(@Param("productId") Long productId);
    Page<Product> findByNameContaining(String searchKeyword, Pageable pageable);

    @Query("SELECT p.status FROM Product p WHERE p.id = :productId")
    boolean findStatusById(@Param("productId") Long productId);

    // 카테고리별 상품 조회
    List<Product> findByCategory_CategoryId(Long categoryId);

}

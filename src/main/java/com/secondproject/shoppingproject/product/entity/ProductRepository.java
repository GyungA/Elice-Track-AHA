package com.secondproject.shoppingproject.product.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findProductById(long id);
    Page<Product> findByNameContaining(String searchKeyword, Pageable pageable );
}

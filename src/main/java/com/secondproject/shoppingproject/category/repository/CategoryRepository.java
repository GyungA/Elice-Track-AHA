package com.secondproject.shoppingproject.category.repository;

import com.secondproject.shoppingproject.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE c.parentId IS NULL")
    List<Category> findParentCategories();

    List<Category> findAllByParentId(Long parentId);

    Long findCategoryIdByName(String categoryName);

    // 네비게이션 카테고리
    List<Category> findByParentIdIsNull();




}


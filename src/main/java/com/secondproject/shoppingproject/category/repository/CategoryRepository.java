package com.secondproject.shoppingproject.category.repository;

import com.secondproject.shoppingproject.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE c.parentCategory IS NULL")
    List<Category> findTopLevelCategories();

    List<Category> findByLevel(Integer level);

    List<Category> findAllByParentCategoryId(Long parentId);

    Category findByName(String categoryName);

    Category findCategoryIdByName(String categoryName);



}


package com.secondproject.shoppingproject.category.service;

import com.secondproject.shoppingproject.category.dto.CategoryDTO;
import com.secondproject.shoppingproject.category.entity.Category;
import com.secondproject.shoppingproject.category.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllTopLevelCategories() {
        return categoryRepository.findTopLevelCategories();
    }

    public List<Category> getAllSubCategories(Long parentId) {
        return categoryRepository.findAllByParentCategoryId(parentId);
    }

}

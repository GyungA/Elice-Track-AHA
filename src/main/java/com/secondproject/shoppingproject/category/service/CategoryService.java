package com.secondproject.shoppingproject.category.service;

import com.secondproject.shoppingproject.category.dto.CategoryDTO;
import com.secondproject.shoppingproject.category.entity.Category;
import com.secondproject.shoppingproject.category.repository.CategoryRepository;
import com.secondproject.shoppingproject.error.CategoryNotFoundException;
import com.secondproject.shoppingproject.product.entity.Product;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> getParentCategories() {
        return categoryRepository.findParentCategories().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public List<CategoryDTO> getAllByParentId(Long parentId) {
        return categoryRepository.findAllByParentId(parentId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Long findCategoryId(String name) {
        return categoryRepository.findCategoryIdByName(name);
    }

    private CategoryDTO convertToDTO(Category category) {
        return new CategoryDTO(category.getCategoryId(), category.getName(), category.getParentId());
    }

    public CategoryDTO addCategory(CategoryDTO categoryDTO) {
        if(StringUtils.isBlank(categoryDTO.getName())) {
            throw new IllegalArgumentException("Category name cannot be empty");
        }

        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setParentId(categoryDTO.getParentId());
        category = categoryRepository.save(category);

        return convertToDTO(category);
    }
    @Transactional
    public void deleteCategory(Long categoryId) {
        // 삭제하기 전에 존재하는지 확인
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category with id: " + categoryId + " not found."));

        categoryRepository.delete(category);
    }


}



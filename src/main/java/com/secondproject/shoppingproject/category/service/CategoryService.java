package com.secondproject.shoppingproject.category.service;

import com.secondproject.shoppingproject.category.dto.CategoryDTO;
import com.secondproject.shoppingproject.category.entity.Category;
import com.secondproject.shoppingproject.category.repository.CategoryRepository;
import com.secondproject.shoppingproject.error.CategoryNotFoundException;
import com.secondproject.shoppingproject.product.entity.Product;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

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


    @Transactional
    public void deleteCategory(Long categoryId) {
        // 삭제하기 전에 존재하는지 확인
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category with id: " + categoryId + " not found."));

        categoryRepository.delete(category);
    }

    // 네비게이션 카테고리
    public List<CategoryDTO> getTopLevelCategories() {
        return categoryRepository.findByParentIdIsNull()
                .stream()
                .map(category -> new CategoryDTO(category.getCategoryId(), category.getName(), category.getParentId()))
                .collect(Collectors.toList());
    }

    // 카테고리 추가

    public CategoryDTO addCategory(CategoryDTO categoryDTO) {
        Category category = new Category(categoryDTO.getName(), categoryDTO.getCategoryId(), categoryDTO.getParentId());
        Category savedCategory = categoryRepository.save(category);

        return new CategoryDTO(savedCategory.getCategoryId(), savedCategory.getName(), savedCategory.getParentId());
    }


}



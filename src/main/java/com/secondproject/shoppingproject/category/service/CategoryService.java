package com.secondproject.shoppingproject.category.service;

import com.secondproject.shoppingproject.category.dto.CategoryDTO;
import com.secondproject.shoppingproject.category.entity.Category;
import com.secondproject.shoppingproject.category.repository.CategoryRepository;
import com.secondproject.shoppingproject.error.CategoryNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;



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

    // 상위 카테고리
    public List<CategoryDTO> getTopLevelCategories() {
        return categoryRepository.findByParentIdIsNull()
                .stream()
                .map(category -> new CategoryDTO(category.getCategoryId(), category.getName(), category.getParentId()))
                .collect(Collectors.toList());
    }

    // 하위 카테고리
    public List<CategoryDTO> getAllByParentId(Long parentId) {
        return categoryRepository.findAllByParentId(parentId)
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


    public CategoryDTO getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with id " + categoryId));
        return convertToDTO(category);
    }



}



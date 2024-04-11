package com.secondproject.shoppingproject.category.service;

import com.secondproject.shoppingproject.category.dto.CategoryDTO;
import com.secondproject.shoppingproject.category.dto.ParentCategoryDTO;
import com.secondproject.shoppingproject.category.entity.Category;
import com.secondproject.shoppingproject.category.repository.CategoryRepository;
import com.secondproject.shoppingproject.error.CategoryAlreadyExistsException;
import com.secondproject.shoppingproject.error.InvalidParentCategoryException;
import jakarta.transaction.Transactional;
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

    public List<Category> findTopLevelCategories() {
        return categoryRepository.findByLevel(1);
    }

    public List<Category> getAllSubCategories(Long parentId) {
        return categoryRepository.findAllByParentCategoryId(parentId);
    }

    @Transactional
    public void addCategory(CategoryDTO categoryDTO, ParentCategoryDTO parentCategoryDTO) throws CategoryAlreadyExistsException, InvalidParentCategoryException {
        String categoryName = categoryDTO.getName();
        String parentCategoryName = parentCategoryDTO.getName();

        Category existingCategory = categoryRepository.findByName(categoryName);

        if (existingCategory != null) {
            throw new CategoryAlreadyExistsException("이미 존재하는 카테고리입니다.");
        }

        if (parentCategoryName == null) {
            Category category = new Category();
            category.setName(categoryName);
            category.setLevel(1);
            categoryRepository.save(category);
        } else {
            Category parentCategory = categoryRepository.findByName(parentCategoryName);
            if (parentCategory == null) {
                throw new InvalidParentCategoryException("상위 카테고리 이름이 올바르지 않습니다.");
            }

            Category category = new Category();
            category.setName(categoryName);
            category.setLevel(2);
            Category parentId = categoryRepository.findCategoryIdByName(parentCategoryName);
            category.setParentCategory(parentId);
            categoryRepository.save(category);
        }

    }

    @Transactional
    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

}



package com.secondproject.shoppingproject.category.controller;

import com.secondproject.shoppingproject.category.dto.CategoryDTO;
import com.secondproject.shoppingproject.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    // 상위 카테고리 조회
    @GetMapping("/top-level")
    public ResponseEntity<List<CategoryDTO>> getTopLevelCategories() {
        List<CategoryDTO> topLevelCategories = categoryService.getTopLevelCategories();
        return ResponseEntity.ok(topLevelCategories);
    }

    @GetMapping("/{parentId}/subcategories")
    public ResponseEntity<List<CategoryDTO>> getSubCategories(@PathVariable("parentId") Long parentId) {
        List<CategoryDTO> subCategories = categoryService.getAllByParentId(parentId);
        return ResponseEntity.ok(subCategories);

    }

    // 카테고리 추가
    @PostMapping("/api/add")
    public ResponseEntity<CategoryDTO> addCateogory(@RequestBody CategoryDTO categoryDTO) {
        CategoryDTO savedCategory = categoryService.addCategory(categoryDTO);
        return ResponseEntity.ok(savedCategory);
    }


}


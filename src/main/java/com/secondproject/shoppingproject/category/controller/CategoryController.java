package com.secondproject.shoppingproject.category.controller;

import com.secondproject.shoppingproject.category.dto.CategoryDTO;
import com.secondproject.shoppingproject.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;


    @GetMapping("/parentCategories")
    public ResponseEntity<?> showParentCategories(Model model) {
//        model.addAttribute("parentCategories", categoryService.getParentCategories());
        return ResponseEntity.ok(categoryService.getParentCategories());
    }

    @GetMapping("/{parentId}")
    public ResponseEntity<?> showSubCategories(@PathVariable("parentId") Long parentId, Model model) {
//        model.addAttribute("subCategories", categoryService.getAllByParentId(parentId));
        return ResponseEntity.ok(categoryService.getAllByParentId(parentId));
    }
//
//    @GetMapping("/add")
//    public String showAddCategoryForm() {
//        return "category-add";
//    }
//
//    @DeleteMapping("/{categoryId}")
//    public ResponseEntity<?> delete(@PathVariable Long categoryId) {
//        categoryService.deleteCategory(categoryId);
//        return ResponseEntity.ok().build();
//    }

    // 상위 카테고리 조회
    @GetMapping("/top-level")
    public ResponseEntity<List<CategoryDTO>> getTopLevelCategories() {
        List<CategoryDTO> topLevelCategories = categoryService.getTopLevelCategories();
        return ResponseEntity.ok(topLevelCategories);
    }

    // 카테고리 추가
    @PostMapping("/api/add")
    public ResponseEntity<CategoryDTO> addCateogory(@RequestBody CategoryDTO categoryDTO) {
        CategoryDTO savedCategory = categoryService.addCategory(categoryDTO);
        return ResponseEntity.ok(savedCategory);
    }
}


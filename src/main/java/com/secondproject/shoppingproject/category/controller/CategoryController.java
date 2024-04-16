package com.secondproject.shoppingproject.category.controller;

import com.secondproject.shoppingproject.category.dto.CategoryDTO;
import com.secondproject.shoppingproject.category.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<?> showParentCategories(Model model) {
//        model.addAttribute("parentCategories", categoryService.getParentCategories());
        return ResponseEntity.ok(categoryService.getParentCategories());
    }

    @GetMapping("/{parentId}")
    public ResponseEntity<?> showSubCategories(@PathVariable("parentId") Long parentId, Model model) {
//        model.addAttribute("subCategories", categoryService.getAllByParentId(parentId));
        return ResponseEntity.ok(categoryService.getAllByParentId(parentId));
    }
    @GetMapping("/add")
    public String showAddCategoryForm() {
        return "category-add";
    }

    @PostMapping
    public ResponseEntity<?> addCategory(@RequestBody CategoryDTO categoryDTO) {
        CategoryDTO newCategory = categoryService.addCategory(categoryDTO);
        return ResponseEntity.ok(newCategory);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> delete(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok().build();
    }


}


package com.secondproject.shoppingproject.category.controller;

import com.secondproject.shoppingproject.category.dto.CategoryDTO;
import com.secondproject.shoppingproject.category.dto.ParentCategoryDTO;
import com.secondproject.shoppingproject.error.CategoryAlreadyExistsException;
import com.secondproject.shoppingproject.category.service.CategoryService;
import com.secondproject.shoppingproject.error.InvalidParentCategoryException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping
    public String showTopLevelCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllTopLevelCategories());
        return "topLevelCategories";
    }

    @GetMapping("/{parentId}")
    public String showSubCategories(@PathVariable("parentId") Long parentId, Model model) {
        model.addAttribute("categories", categoryService.getAllSubCategories(parentId));
        return "subCategories";
    }
    @GetMapping("/add")
    public String showAddCategoryForm() {
        return "addCategory";
    }


    @PostMapping("/add")
    public ResponseEntity<String> addCategory(@RequestParam("name") String name, @RequestParam("parent") String parentCategoryName) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName(name);
        ParentCategoryDTO parentCategoryDTO = new ParentCategoryDTO();
        parentCategoryDTO.setName(parentCategoryName);

        try {
            categoryService.addCategory(categoryDTO, parentCategoryDTO);
            return ResponseEntity.ok("카테고리가 성공적으로 추가되었습니다.");
        } catch (CategoryAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (InvalidParentCategoryException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/{categoryId}")
    public ResponseEntity<String> deleteCateogry(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok("카테고리 삭제 완료");
    }



}


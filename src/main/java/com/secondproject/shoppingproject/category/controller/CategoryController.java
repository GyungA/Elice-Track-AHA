package com.secondproject.shoppingproject.category.controller;

import com.secondproject.shoppingproject.category.service.CategoryService;
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

}


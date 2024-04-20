package com.secondproject.shoppingproject.category.controller;

import com.secondproject.shoppingproject.category.dto.CategoryDTO;
import com.secondproject.shoppingproject.category.service.CategoryService;
import com.secondproject.shoppingproject.product.entity.Product;
import com.secondproject.shoppingproject.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("categories")
public class CategoryViewController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;


    // 카테고리별 상품 페이지
    @GetMapping("/food")
    public String productView(Model model) {

        return "productByCategory";
    }

    @GetMapping("/{parentId}/sub")
    public String getSubCategories(@PathVariable("parentId") Long parentId, Model model) {
        List<CategoryDTO> subCategories = categoryService.getAllByParentId(parentId);
//        long parent_id = subCategories.get(0).getParentId();
//        List<CategoryDTO> parent_category = categoryService.getById(categoryId);
//        model.addAttribute("parent_category", parent_category);
//        List<Product> products = productService.get
        model.addAttribute("subCategories", subCategories);
        return "productByCategory";
    }

//    @GetMapping("/parentCategory")
//    public String getParentCategory(Model model) {
//        List<CategoryDTO> parentCategory = categoryService.getTopLevelCategories();
//        long parentId = parentCategory.get(0).getParentId();
//        CategoryDTO parent_category = categoryService.get
//        model.addAttribute("parentCategory", parent_category);
//        return "category-product/productByCategory"; //
//    }

    @GetMapping("/sub/{categoryId}")
    public String getCategoryProducts(@PathVariable("categoryId") Long categoryId, Model model) {
        CategoryDTO category = categoryService.getCategoryById(categoryId); // 카테고리 정보를 조회
        List<Product> products = productService.getAllProductsByCategoryId(categoryId); // 해당 카테고리의 상품 목록 조회

        model.addAttribute("category", category);
        model.addAttribute("products", products);

        return "productByCategory"; // 뷰 이름 반환
    }

    @GetMapping("/add")
    public String addCategory() {
        return "category-add";
    }



}

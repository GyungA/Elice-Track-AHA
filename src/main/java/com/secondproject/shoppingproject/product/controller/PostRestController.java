package com.secondproject.shoppingproject.product.controller;

import com.secondproject.shoppingproject.product.dto.ProductAddRequestDto;
import com.secondproject.shoppingproject.product.entity.Product;
import com.secondproject.shoppingproject.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostRestController {
    private final ProductService productService;

    // 상품 등록(POST)
    @PostMapping("/product/new/pro")
    public ResponseEntity<String> productSave(ProductAddRequestDto requestDto){
        productService.saveProduct(requestDto);
        return ResponseEntity.ok("");
    }

    // 상품 리스트 페이지
    @GetMapping("/product/list/{id}")
    public ResponseEntity<String> productList(Model model, @PathVariable("id") Long id, Pageable pageable, String searchKeyword){
        Page<Product> products = null;
        if(searchKeyword ==  null){ // 검색어가 주어지지 않은 경우 모든 항목을 페이지별로 가져오기
            products = productService.allProductViewPage(pageable);
        }else{ // 검색어가 주어진 경우 해당 키워드로 항목 검색하여 페이지별로 가져오기
            products =productService.productSearchList(searchKeyword, pageable);
        }

        int nowPage = products.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage -4 , 1);
        int endPage = Math.min(nowPage +5, products.getTotalPages());

        model.addAttribute("products", products);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return ResponseEntity.ok("");

    }
}

package com.secondproject.shoppingproject.product.controller;

import com.secondproject.shoppingproject.product.dto.ProductAddRequestDto;
import com.secondproject.shoppingproject.product.entity.Product;
import com.secondproject.shoppingproject.product.entity.ProductRepository;
import com.secondproject.shoppingproject.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // 상품 등록 페이지(GET)
    @GetMapping("/product/new")
    public String productSaveForm(){
        return "/seller/productForm";
    }

    // 상품 등록(POST)
//    @PostMapping("/product/new/pro")
//    public String productSave(Product product){
//        productService.saveProduct(product);
//        return "/main";
//    }

    // 상품 수정 페이지(GET)
    @GetMapping("/product/modify/{id}")
    public String productModifyForm(@PathVariable("id") Long id, Model model){
        model.addAttribute("product", productService.productView(id));
        return "/seller/productModify";
    }

    // 상품 수정(POST)
    @PostMapping("/product/modify/pro/{id}")
    public String productModify(Product product, @PathVariable("id") Long id){
        productService.productModify(product, id);
        return "redirect:/main";
    }

    // 상품 삭제
//    @GetMapping("/product/delete/{id}")
//    public String productDelete(@PathVariable("id") Long id){
//        productService.productDelete(id);
//        return "/main";
//    }

    // 상품 상세 페이지
    @GetMapping("/product/view/{id}")
    public String productView(Model model, @PathVariable("id") Long id){
        model.addAttribute("product", productService.productView(id));
        return "/seller/productView";
    }

    // 상품 리스트 페이지
//    @GetMapping("/product/list/{id}")
//    public String productList(Model model, @PathVariable("id") Long id, Pageable pageable, String searchKeyword){
//        Page<Product> products = null;
//        if(searchKeyword ==  null){ // 검색어가 주어지지 않은 경우 모든 항목을 페이지별로 가져오기
//            products = productService.allProductViewPage(pageable);
//        }else{ // 검색어가 주어진 경우 해당 키워드로 항목 검색하여 페이지별로 가져오기
//            products =productService.productSearchList(searchKeyword, pageable);
//        }
//
//        int nowPage = products.getPageable().getPageNumber() + 1;
//        int startPage = Math.max(nowPage -4 , 1);
//        int endPage = Math.min(nowPage +5, products.getTotalPages());
//
//        //상품추가 모달 입력폼
//        ProductAddRequestDto productAddRequestDto = new ProductAddRequestDto();
//        model.addAttribute("productAdd", productAddRequestDto);
//
//        model.addAttribute("products", products);
//        model.addAttribute("nowPage", nowPage);
//        model.addAttribute("startPage", startPage);
//        model.addAttribute("endPage", endPage);
//
//        return "/product-management";
//
//    }
}

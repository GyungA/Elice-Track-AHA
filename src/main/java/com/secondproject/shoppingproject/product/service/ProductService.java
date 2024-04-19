package com.secondproject.shoppingproject.product.service;


import com.secondproject.shoppingproject.product.entity.Product;
import com.secondproject.shoppingproject.product.entity.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    //상품 등록
    public void saveProduct(Product product) { productRepository.save(product);}
    //상품 개별 불러오기
    public Product productView(Long id){return productRepository.findById(id).get();}
    //상품 리스트 불러오기
    public List<Product> allProductView(){return productRepository.findAll();}
    //상품 리스트 페이지용 불러오기
    public Page<Product> allProductViewPage(Pageable pageable){return productRepository.findAll(pageable);}

    // 상품 수정
    public void productModify(Product product, Long id){
        Product update = productRepository.findProductById(id);
        update.setName(product.getName());
        update.setDescription(product.getDescription());
        update.setPrice(product.getPrice());
        update.setCurrent_stock(product.getCurrent_stock());
        productRepository.save(update);
    }

    // 상품 삭제
    public void productDelete(Long id){ productRepository.deleteById(id);}

    // 상품 검색
    public Page<Product> productSearchList(String searchKeyword, Pageable pageable) {

        return productRepository.findByNameContaining(searchKeyword, pageable);
    }

}

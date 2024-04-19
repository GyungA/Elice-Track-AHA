package com.secondproject.shoppingproject.product.service;


import com.secondproject.shoppingproject.category.entity.Category;
import com.secondproject.shoppingproject.category.repository.CategoryRepository;
import com.secondproject.shoppingproject.order.exception.EntityNotFoundException;
import com.secondproject.shoppingproject.product.dto.ProductAddRequestDto;
import com.secondproject.shoppingproject.product.entity.Product;
import com.secondproject.shoppingproject.product.entity.ProductRepository;
import com.secondproject.shoppingproject.user.entity.User;
import com.secondproject.shoppingproject.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    //상품 등록
    @Transactional
    public void saveProduct(ProductAddRequestDto requestDto) {
        Category category = categoryRepository.findById(requestDto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("해당하는 카테고리를 찾을 수 없습니다."));
        User user = userRepository.findById(requestDto.getSellerId())
                .orElseThrow(() -> new EntityNotFoundException("해당하는 user id를 찾을 수 없습니다."));

        Product product = Product.builder()
                .category(category)
                .price(requestDto.getPrice())
                .name(requestDto.getName())
                .description("")
                .current_stock(requestDto.getStock())
                .seller(user)
                .image("")
                .build();

        if (product.getCurrent_stock() > 0) product.setStatus(true);
        else product.setStatus(false);

        productRepository.save(product);
    }

    //상품 개별 불러오기
    public Product productView(Long id) {
        return productRepository.findById(id).get();
    }

    //상품 리스트 불러오기
    public List<Product> allProductView() {
        return productRepository.findAll();
    }

    //상품 리스트 페이지용 불러오기
    public Page<Product> allProductViewPage(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    // 상품 수정
    public void productModify(Product product, Long id) {
        Product update = productRepository.findProductById(id);
        update.setName(product.getName());
        update.setDescription(product.getDescription());
        update.setPrice(product.getPrice());
        update.setCurrent_stock(product.getCurrent_stock());
        productRepository.save(update);
    }

    // 상품 삭제
    public void productDelete(Long id) {
        productRepository.deleteById(id);
    }

    // 상품 검색
    public Page<Product> productSearchList(String searchKeyword, Pageable pageable) {

        return productRepository.findByNameContaining(searchKeyword, pageable);
    }

}

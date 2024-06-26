package com.secondproject.shoppingproject.product.entity;


import com.secondproject.shoppingproject.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int price; //가격
    private String name; //상품명
    private String description; // 상품설명
    private boolean status; //상태
    private int current_stock; // 현재 재고

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seller_id")
    private User seller; // 판매자 아이디

    private String image; // 상품 이미지

    @DateTimeFormat(pattern = "YYYY-MM-DD")
    private LocalDate createAt; // 상품 등록 날짜

    @PrePersist // DB에 INSERT 되기 직전에 실행. 즉 DB에 값을 넣으면 자동으로 실행됨
    public void createDate() {
        this.createAt = LocalDate.now();
    }

}

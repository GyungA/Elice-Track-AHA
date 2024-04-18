package com.secondproject.shoppingproject.category.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;




@Entity
@NoArgsConstructor
@Getter @Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    private String name;

    private Long parentId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "parent_key")
//    private Category parentCategory;

//    @OneToMany(mappedBy = "parentCategory")
//    private List<Category> subCategory = new ArrayList<>();

    public Category(String name, Long categoryId,Long parentId) {
        this.name = name;
        this.categoryId = categoryId;
        this.parentId = parentId;
    }
}

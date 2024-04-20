package com.secondproject.shoppingproject.category.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter @Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    private String name;

    private Long parentId;


    public Category(String name, Long categoryId,Long parentId) {
        this.name = name;
        this.categoryId = categoryId;
        this.parentId = parentId;
    }
}

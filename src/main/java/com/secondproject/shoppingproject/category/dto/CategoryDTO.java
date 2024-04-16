package com.secondproject.shoppingproject.category.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter @Setter
@NoArgsConstructor
public class CategoryDTO {

    private Long categoryId;
    private String name;
    private Long parentId;

    private List<CategoryDTO> subCategories;

    public CategoryDTO(Long categoryId, String name, Long parentId) {
        this.categoryId = categoryId;
        this.name = name;
        this.parentId = parentId;
    }




}



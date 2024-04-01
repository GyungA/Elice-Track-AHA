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
    private List<CategoryDTO> subCategories;
    private String parentName;
    private Integer level;


}



package com.secondproject.shoppingproject.category.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter @Setter
@NoArgsConstructor
public class CategoryDTO {

    private Long id;
    private String name;
    private Integer level;
    private Long parentId;

    private List<CategoryDTO> subCategories;

    public CategoryDTO(Long id, String name, Long parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }




}



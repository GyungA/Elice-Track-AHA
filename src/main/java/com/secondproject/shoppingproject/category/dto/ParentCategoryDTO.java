package com.secondproject.shoppingproject.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParentCategoryDTO {

    private Long id;
    private String name;
    private Integer level;
}

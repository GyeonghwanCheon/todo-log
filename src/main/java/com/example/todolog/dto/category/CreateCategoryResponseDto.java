package com.example.todolog.dto.category;

import lombok.Getter;

@Getter
public class CreateCategoryResponseDto {

    private final Long id;

    private final Long parentId;

    private final String name;

    public CreateCategoryResponseDto(Long id, Long parentId, String name) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }
}

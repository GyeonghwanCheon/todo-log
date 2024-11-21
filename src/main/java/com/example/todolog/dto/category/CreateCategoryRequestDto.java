package com.example.todolog.dto.category;

import lombok.Getter;

@Getter
public class CreateCategoryRequestDto {

    private final Long parentId;

    private final String name;

    public CreateCategoryRequestDto(Long parentId, String name) {
        this.parentId = parentId;
        this.name = name;
    }
}

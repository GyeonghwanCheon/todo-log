package com.example.todolog.dto.category;

import lombok.Getter;

@Getter
public class CategoryResponseDto {

    private final String firstCategory;

    private final String secondCategory;

    private final String thirdCategory;


    public CategoryResponseDto(String firstCategory, String secondCategory, String thirdCategory) {
        this.firstCategory = firstCategory;
        this.secondCategory = secondCategory;
        this.thirdCategory = thirdCategory;
    }
}

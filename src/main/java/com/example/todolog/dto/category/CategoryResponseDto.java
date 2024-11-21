package com.example.todolog.dto.category;

import lombok.Getter;

@Getter
public class CategoryResponseDto {

    private final String firstCategory;

    private final String secondCategory;

    private final String thirdCategory;

    private final Integer stepCategory;


    public CategoryResponseDto(String firstCategory, String secondCategory, String thirdCategory, Integer stepCategory) {
        this.firstCategory = firstCategory;
        this.secondCategory = secondCategory;
        this.thirdCategory = thirdCategory;
        this.stepCategory = stepCategory;
    }
}

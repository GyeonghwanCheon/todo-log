package com.example.todolog.dto.feeddto;

import lombok.Getter;

@Getter
public class FeedRequestDto {

    private final Long userId;
    private final String title;
    private final String detail;

    public FeedRequestDto(Long userId,String title, String detail) {
        this.userId = userId;
        this.title = title;
        this.detail = detail;
    }
}

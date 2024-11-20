package com.example.todolog.dto.feeddto;

import lombok.Getter;

@Getter
public class FeedRequestDto {

    private final Long userId;
    private final String title;
    private final String contents;

    public FeedRequestDto(Long userId, String title, String contents) {
        this.userId = userId;
        this.title = title;
        this.contents = contents;
    }
}

package com.example.todolog.dto.feeddto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FeedUpdateRequestDto {

    private final String title;
    private final String detail;
    private final LocalDateTime updateAt;

    public FeedUpdateRequestDto(String title, String detail, LocalDateTime updateAt) {
        this.title = title;
        this.detail = detail;
        this.updateAt = updateAt;
    }
}

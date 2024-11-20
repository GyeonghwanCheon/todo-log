package com.example.todolog.dto.feeddto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FeedUpdateRequestDto {

    private final String nickname;
    private final String title;
    private final String detail;
    private final LocalDateTime updatedAt;

    public FeedUpdateRequestDto(String nickname ,String title, String detail, LocalDateTime updatedAt) {
        this.nickname = nickname;
        this.title = title;
        this.detail = detail;
        this.updatedAt = updatedAt;
    }
}

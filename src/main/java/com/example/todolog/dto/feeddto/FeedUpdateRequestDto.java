package com.example.todolog.dto.feeddto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FeedUpdateRequestDto {

    private final String title;
    private final String contents;
    private final LocalDateTime updateAt;

    public FeedUpdateRequestDto(String title, String contents, LocalDateTime updateAt) {
        this.title = title;
        this.contents = contents;
        this.updateAt = updateAt;
    }
}

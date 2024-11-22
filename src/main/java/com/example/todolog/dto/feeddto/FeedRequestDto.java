package com.example.todolog.dto.feeddto;

import lombok.Getter;

@Getter
public class FeedRequestDto {

    private final Long userId;
    private final Long categoryid;
    private final String nickname;
    private final String title;
    private final String detail;

    public FeedRequestDto(Long userId, Long categoryid, String nickname, String title, String detail) {
        this.userId = userId;
        this.categoryid = categoryid;
        this.nickname = nickname;
        this.title = title;
        this.detail = detail;
    }
}

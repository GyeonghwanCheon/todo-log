package com.example.todolog.dto.comment;

import lombok.Getter;

@Getter
public class CommentResponseDto {
    private final Long id;

    private final Long feedId;

    private final String nickName;

    private final String detail;

    public CommentResponseDto(Long id, Long feedId, String nickName, String detail) {
        this.id = id;
        this.feedId = feedId;
        this.nickName = nickName;
        this.detail = detail;
    }
}

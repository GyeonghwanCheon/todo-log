package com.example.todolog.dto.like;


import lombok.Getter;

@Getter
public class LikeFeedResponseDto {

    private final Long id;

    private final Long feedId;

    private final Long userId;

    private final Integer likeStatus;

    public LikeFeedResponseDto(Long id, Long feedId, Long userId, Integer likeStatus) {
        this.id = id;
        this.feedId = feedId;
        this.userId = userId;
        this.likeStatus = likeStatus;
    }
}
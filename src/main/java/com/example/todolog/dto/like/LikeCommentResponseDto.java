package com.example.todolog.dto.like;


import lombok.Getter;

@Getter
public class LikeCommentResponseDto {

    private final Long id;

    private final Long commentId;

    private final Long userId;

    private final Integer likeStatus;

    public LikeCommentResponseDto(Long id, Long commentId, Long userId, Integer likeStatus) {
        this.id = id;
        this.commentId = commentId;
        this.userId = userId;
        this.likeStatus = likeStatus;
    }
}

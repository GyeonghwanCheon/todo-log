package com.example.todolog.dto.like;


import lombok.Getter;

@Getter
public class LikeCommentResponseDto {

    private final Long id;

    private final Long commentId;

    private final Long userId;

    private final Boolean likeStatus;

    public LikeCommentResponseDto(Long id, Long commentId, Long userId, Boolean likeStatus) {
        this.id = id;
        this.commentId = commentId;
        this.userId = userId;
        this.likeStatus = likeStatus;
    }
}

package com.example.todolog.dto.like;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class LikeCommentRequestDto {

    @NotNull(message = "commentId 은 필수값 입니다.")
    private final Long commentId;

    @NotNull(message = "userId 은 필수값 입니다.")
    private final Long userId;

    public LikeCommentRequestDto(Long commentId, Long userId) {
        this.commentId = commentId;
        this.userId = userId;
    }
}

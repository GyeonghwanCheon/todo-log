package com.example.todolog.dto.like;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class LikeFeedRequestDto {

    @NotNull(message = "feedId 은 필수값 입니다.")
    private final Long feedId;

    @NotNull(message = "userId 은 필수값 입니다.")
    private final Long userId;

    public LikeFeedRequestDto(Long feedId, Long userId) {
        this.feedId = feedId;
        this.userId = userId;
    }
}

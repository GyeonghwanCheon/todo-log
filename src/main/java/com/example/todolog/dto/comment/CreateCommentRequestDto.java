package com.example.todolog.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateCommentRequestDto {

    @NotNull(message = "feedId 는 필수값 입니다.")
    private final Long feedId;

    @NotBlank(message = "detail 은 필수값 입니다.")
    @Size(min = 1, max = 20, message = "detail 은 20글자 이내여야 합니다.")
    private final String detail;


    public CreateCommentRequestDto(Long feedId, String detail) {
        this.feedId = feedId;
        this.detail = detail;
    }

}

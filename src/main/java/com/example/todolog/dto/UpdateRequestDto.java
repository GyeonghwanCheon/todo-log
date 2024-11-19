package com.example.todolog.dto;

import lombok.Getter;

// 유저 프로필 수정 Dto
@Getter
public class UpdateRequestDto {

    // 수정 할 mbti
    private final String newMbti;

    // 수정 할 상태 메세지
    private final String newStatusMs;

    // 생성자
    public UpdateRequestDto(String newMbti, String newStatusMs) {
        this.newMbti = newMbti;
        this.newStatusMs = newStatusMs;
    }
}

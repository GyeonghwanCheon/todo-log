package com.example.todolog.dto;

import lombok.Getter;

// 로그인 응답 Dto
@Getter
public class SignupResponseDto {

    private final Long id;

    private final String nickname;

    public SignupResponseDto(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }
}

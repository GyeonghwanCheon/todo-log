package com.example.todolog.dto;

import lombok.Getter;

// 회원가입 응답 Dto
@Getter
public class SignupResponseDto {

    // Key 값
    private final Long id;

    // 닉네임 ( 이름 )
    private final String nickname;

    // 이메일
    private final String email;

    // 생성자
    public SignupResponseDto(Long id, String nickname, String email) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
    }
}

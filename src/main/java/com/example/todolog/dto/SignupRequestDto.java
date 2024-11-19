package com.example.todolog.dto;

import lombok.Getter;

// 로그인 요청 DTO
@Getter
public class SignupRequestDto {

    // 닉네임(이름)
    private final String nickname;

    // 이메일
    private final String email;

    // 비밀번호
    private final String password;

    // 생성자
    public SignupRequestDto(String nickname, String email, String password) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }
}

package com.example.todolog.dto;

import lombok.Getter;

@Getter
// 유저 조회 응답 Dto
public class UserResponseDto {

    // 닉네임 (이름)
    private String nickname;

    // 이메일
    private String email;

    // mbti
    private String mbti;

    // 상태메세지
    private String statusMs;

    // 생성자
    public UserResponseDto(String nickname, String email, String mbti, String statusMs) {
        this.nickname = nickname;
        this.email = email;
        this.mbti = mbti;
        this.statusMs = statusMs;
    }
}

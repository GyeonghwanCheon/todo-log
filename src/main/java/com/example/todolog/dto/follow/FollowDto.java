package com.example.todolog.dto.follow;

import lombok.Getter;

@Getter
public class FollowDto {

    // 닉네임 (이름)
    private String nickname;

    // 이메일
    private String email;

    //mbti
    private String mbti;

    // 상태메세지
    private String statusMs;

    public FollowDto(String nickname, String email, String mbti, String statusMs) {
        this.nickname = nickname;
        this.email = email;
        this.mbti = mbti;
        this.statusMs = statusMs;
    }

}

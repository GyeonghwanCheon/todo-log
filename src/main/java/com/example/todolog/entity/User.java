package com.example.todolog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// 유저 데이터
@Getter
@Entity
@Table(name = "`user`")
public class User extends BaseEntity {

    // Key 값
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 닉네임 (이름)
    @Column(nullable = false)
    private String nickname;

    // 비밀번호
    @Column(nullable = false)
    private String password;

    // 이메일
    @Column(nullable = false, unique = true)
    private String email;

    // MBTI
    private String mbti;

    // 상태메세지
    private String statusMs;

    // 탈퇴여부
    @Setter
    private boolean banned;

    // 기본 생성자
    public User(){
    }

    // 생성자 (회원가입)
    public User(String nickname, String password, String email) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }
    // 생성자 (유저 프로필 정보 수정)
    public void updateProfile(String mbti, String statusMs) {
        this.mbti = mbti;
        this.statusMs = statusMs;
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}

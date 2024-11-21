package com.example.todolog.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

// 유저 데이터
@Getter
@Entity
@Table(name = "user")
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

    // 기본 생성자
    public User(){
    }

    // 생성자 (회원가입)
    public User( String nickname, String password, String email) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }
    // 생성자 (유저 프로필 정보 수정)
    public void updateUser(String mbti, String statusMs) {
        this.mbti = mbti;
        this.statusMs = statusMs;
    }
}

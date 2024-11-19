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

    // 닉네임 ( 이름 )
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

    // 기본 생성자
    public User(){
    }

    // 생성자
    public User(String nickname, String password, String email, String mbti) {
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.mbti = mbti;
    }
    public User( String nickname, String password, String email) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }
}

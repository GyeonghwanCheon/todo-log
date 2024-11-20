package com.example.todolog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "feed")
public class Feed extends BaseEntity {

    // 식별자
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 제목
    private String title;

    // 내용
    @Column(columnDefinition = "longtext")
    private String contents;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Feed(User user, String title, String contents) {
        this.user = user;
        this.title = title;
        this.contents = contents;
    }

    // 기본 생성자
    public Feed() {}

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public void setUser(User user) {
        this.user = user;
    }


}

package com.example.todolog.entity;

import jakarta.persistence.*;
import lombok.Getter;

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
    private String detail;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Feed(String title, String detail) {
        this.title = title;
        this.detail = detail;
    }

    // 기본 생성자
    public Feed() {}

    public void updateFeed(String title, String detail) {
        this.title = title;
        this.detail = detail;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}

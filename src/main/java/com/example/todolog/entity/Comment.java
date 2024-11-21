package com.example.todolog.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @Setter
    @ManyToOne
    @JoinColumn(name = "feed_id")
    private Feed feed;

    //내용
    private String detail;

    public Comment() {

    }

    public Comment(String  detail){
        this.detail=detail;
    }

    public void updateDetail(String detail){
        this.detail=detail;
    }


}

package com.example.todolog.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "\"like\"")
public class Like extends BaseEntity{

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

    @Setter
    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    private Boolean likeStatus;

    public Like() {
    }

    public void updateLikeStatus(Boolean likeStatus){
        this.likeStatus = likeStatus;
    }
}

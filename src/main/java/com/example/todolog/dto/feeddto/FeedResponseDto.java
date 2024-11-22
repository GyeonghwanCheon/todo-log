package com.example.todolog.dto.feeddto;

import com.example.todolog.entity.Feed;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;


import java.time.LocalDateTime;


@Getter
public class FeedResponseDto {

    private final Long id;
    private final String nickname;
    private final String categoryname;
    private final String title;
    private final String detail;

    @Setter
    private int likeCount;

    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public FeedResponseDto(Long id, String categoryname, String nickname, String title, String detail, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.nickname = nickname;
        this.categoryname = categoryname;
        this.title = title;
        this.detail = detail;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static FeedResponseDto feedDto (Feed feed) {
        return new FeedResponseDto(
                feed.getId(),
                feed.getCategory().getName(),
                feed.getUser().getNickname(),
                feed.getTitle(),
                feed.getDetail(),
                feed.getCreatedAt(),
                feed.getUpdatedAt()
        );
    }
}

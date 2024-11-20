package com.example.todolog.dto.feeddto;

import com.example.todolog.entity.Feed;


import java.time.LocalDateTime;

public class FeedResponseDto {

    private final Long id;
    private final String title;
    private final String contents;
    private final LocalDateTime createAt;
    private final LocalDateTime updateAt;

    public FeedResponseDto(Long id, String title, String contents, LocalDateTime createAt, LocalDateTime updateAt) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public static FeedResponseDto feedDto (Feed feed) {
        return new FeedResponseDto(
                feed.getId(),
                feed.getTitle(),
                feed.getContents(),
                feed.getCreatedAt(),
                feed.getUpdatedAt()
        );
    }
}

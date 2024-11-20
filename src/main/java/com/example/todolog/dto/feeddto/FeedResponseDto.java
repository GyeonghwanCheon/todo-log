package com.example.todolog.dto.feeddto;

import com.example.todolog.entity.Feed;
import lombok.Getter;


import java.time.LocalDateTime;

@Getter
public class FeedResponseDto {

    private final Long id;
    private final String writerName;
    private final String title;
    private final String contents;
    private final LocalDateTime createAt;
    private final LocalDateTime updateAt;

    public FeedResponseDto(Long id, String writerName, String title, String contents, LocalDateTime createAt, LocalDateTime updateAt) {
        this.id = id;
        this.writerName = writerName;
        this.title = title;
        this.contents = contents;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public static FeedResponseDto feedDto (Feed feed) {
        return new FeedResponseDto(
                feed.getId(),
                feed.getUser().getNickname(),
                feed.getTitle(),
                feed.getContents(),
                feed.getCreatedAt(),
                feed.getUpdatedAt()
        );
    }
}

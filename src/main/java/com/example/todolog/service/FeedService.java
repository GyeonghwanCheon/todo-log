package com.example.todolog.service;

import com.example.todolog.dto.feeddto.FeedRequestDto;
import com.example.todolog.dto.feeddto.FeedResponseDto;
import com.example.todolog.entity.Feed;
import com.example.todolog.repository.FeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final FeedRepository feedRepository;

    public List<FeedResponseDto> findAll() {
        List<Feed> feed = feedRepository.findAll();

        return feed.stream().map(FeedResponseDto::feedDto).toList();
    }

    public FeedResponseDto findById(Long id) {
        return FeedResponseDto.feedDto(findFeedById(id));
    }

    private Feed findFeedById(Long id){
        return feedRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("잘못된 ID 입니다."));
    }
}

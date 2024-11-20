package com.example.todolog.service;

import com.example.todolog.dto.feeddto.FeedRequestDto;
import com.example.todolog.dto.feeddto.FeedResponseDto;
import com.example.todolog.dto.feeddto.FeedUpdateRequestDto;
import com.example.todolog.entity.Feed;
import com.example.todolog.entity.User;
import com.example.todolog.repository.FeedRepository;
import com.example.todolog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final FeedRepository feedRepository;
    private final UserRepository userRepository;

    // 피드 전체 조회
    public List<FeedResponseDto> findAll() {
        List<Feed> feed = feedRepository.findAll();

        return feed.stream().map(FeedResponseDto::feedDto).toList();
    }

    // 피드 단건 조회
    public FeedResponseDto findById(Long id) {
        return FeedResponseDto.feedDto(findFeedById(id));
    }

    private Feed findFeedById(Long id){
        return feedRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("잘못된 ID 입니다."));
    }


    // 피드 삭제
    public void deleteFeed(Long id) {
        findFeedById(id);
        feedRepository.deleteById(id);
    }

    // 피드 생성
    @Transactional
    public FeedResponseDto createFeed(FeedRequestDto feedRequestDto) {

        User finduser = userRepository.findByIdOrElseThrow(feedRequestDto.getUserId());

        User savedUser = userRepository.save(finduser);

        Feed feed = new Feed(savedUser, feedRequestDto.getTitle(), feedRequestDto.getContents());

        Feed savedFeed = feedRepository.save(feed);

        return FeedResponseDto.feedDto(savedFeed);
    }

    @Transactional
    public FeedResponseDto updateFeed(Long id, FeedUpdateRequestDto updateRequestDto) {
        Feed feed = findFeedById(id);
        feed.update(updateRequestDto.getTitle(), updateRequestDto.getContents());
        return FeedResponseDto.feedDto(feed);
    }
}

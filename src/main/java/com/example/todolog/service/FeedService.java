package com.example.todolog.service;


import com.example.todolog.dto.feeddto.FeedResponseDto;
import com.example.todolog.entity.Feed;
import com.example.todolog.entity.User;
import com.example.todolog.repository.FeedRepository;
import com.example.todolog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final FeedRepository feedRepository;
    private final UserRepository userRepository;


    // 피드 전체 조회
    public List<FeedResponseDto> findAll() {

        return feedRepository.findAll().stream().map(FeedResponseDto::feedDto).toList();
    }

    // 피드 단건 조회
    public FeedResponseDto findById(Long id) {
        Feed findFeed = feedRepository.findByOrElseThrow(id);

        User user = findFeed.getUser();

        return new FeedResponseDto(
                findFeed.getId(), user.getNickname(), findFeed.getTitle(), findFeed.getDetail(),
                findFeed.getCreatedAt(), findFeed.getUpdatedAt()
        );

    }


    // 피드 삭제
    public void deleteFeed(Long id) {
        Feed findFeed = feedRepository.findByOrElseThrow(id);
        feedRepository.delete(findFeed);
    }

    // 피드 생성
    public FeedResponseDto save(Long userId, String title, String contents) {

        User findUser = userRepository.findByIdOrElseThrow(userId);

        Feed feed = new Feed(title, contents);
        feed.setUser(findUser);

        Feed savedFeed = feedRepository.save(feed);

        User nickname = feed.getUser();

        return new FeedResponseDto(savedFeed.getId(), nickname.getNickname(), savedFeed.getTitle(),
                savedFeed.getDetail(),savedFeed.getCreatedAt(), savedFeed.getUpdatedAt());
    }


    // 피드 수정
    @Transactional
    public void updateFeed(Long id, String title, String contents) {

        Feed findFeed = feedRepository.findByOrElseThrow(id);

        if(findFeed.getId() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID가 없습니다.");
        }

        if(title == null || contents == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "제목 또는 내용이 없습니다.");
        }

        findFeed.updateFeed(title, contents);
    }

}

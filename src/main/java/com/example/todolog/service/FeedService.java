package com.example.todolog.service;


import com.example.todolog.dto.feeddto.FeedResponseDto;
import com.example.todolog.entity.Feed;
import com.example.todolog.entity.User;
import com.example.todolog.repository.FeedRepository;
import com.example.todolog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final FeedRepository feedRepository;
    private final UserRepository userRepository;


    // 피드 전체 조회
    public List<FeedResponseDto> findAll() {
        List<Feed> feed = feedRepository.findAll();

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
    public void deleteFeed(Long id, String nickname) {
        Feed findFeed = feedRepository.findByOrElseThrow(id);

        if(findFeed != null) {

            if(Objects.equals(findFeed.getUser().getNickname(), nickname)) {
                feedRepository.delete(findFeed);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "유저 아이디가 일치하지 않습니다.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID 값이 없습니다. = " + id);
        }
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
    public void updateFeed(Long id, String nickname, String title, String contents) {

        Feed findFeed = feedRepository.findByOrElseThrow(id);

        if(!Objects.equals(findFeed.getUser().getNickname(), nickname)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "유저 아이디가 일치하지 않습니다.");
        }

        if(findFeed.getId() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID가 없습니다.");
        }

        if(title == null || contents == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "제목 또는 내용이 없습니다.");
        }

        findFeed.updateFeed(title, contents);
    }

    // 페이징 조회
    public List<FeedResponseDto> findFeedByPageRequest(Pageable pageable) {

        List<FeedResponseDto> feedResponseDtoList = new ArrayList<>();
        Page<Feed> feedPage = feedRepository.findAll(pageable);
        List<Feed> feedList = feedPage.getContent();
        // Feed에서 User 객체를 제외하고 나머지 필드들만 불러와서 반환.

        for (Feed feed : feedList) {
            //FeedResponseDto에 Feed 필드를 넣는다.
            FeedResponseDto feedResponseDto = new FeedResponseDto(
                    feed.getId(),
                    feed.getUser().getNickname(),
                    feed.getTitle(),
                    feed.getDetail(),
                    feed.getCreatedAt(),
                    feed.getUpdatedAt()
            );
            feedResponseDtoList.add(feedResponseDto);
        }

        return feedResponseDtoList;
//        return feedRepository.findAll(pageable).getContent().stream().map(FeedResponseDto::feedDto).toList();
    }
}

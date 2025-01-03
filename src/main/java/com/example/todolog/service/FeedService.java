package com.example.todolog.service;


import com.example.todolog.dto.feeddto.FeedResponseDto;
import com.example.todolog.entity.Category;
import com.example.todolog.entity.Feed;
import com.example.todolog.entity.User;
import com.example.todolog.error.exception.CustomException;
import com.example.todolog.repository.CategoryRepository;
import com.example.todolog.repository.FeedRepository;
import com.example.todolog.repository.LikeRepository;
import com.example.todolog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.todolog.error.errorcode.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final FeedRepository feedRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final LikeRepository likeRepository;


    // 피드 전체 조회
    public List<FeedResponseDto> findAll() {
        List<Feed> feeds = feedRepository.findAll(Sort.by(Sort.Direction.DESC, "updatedAt"));

        // Feed -> FeedResponseDto 변환
        List<FeedResponseDto> responseDtos = new ArrayList<>();
        for (Feed feed : feeds) {
            // Feed 데이터를 DTO로 변환
            FeedResponseDto dto = new FeedResponseDto(
                    feed.getId(),
                    feed.getCategory().getName(),
                    feed.getUser().getNickname(),
                    feed.getTitle(),
                    feed.getDetail(),
                    feed.getCreatedAt(),
                    feed.getUpdatedAt()
            );
            // 좋아요 수 조회
            int likeCount = likeRepository.countByFeed_IdAndLikeStatus(feed.getId(), true);
            dto.setLikeCount(likeCount); // 좋아요 수 설정

            responseDtos.add(dto);
        }
        return responseDtos;
    }


    // 피드 단건 조회
    public FeedResponseDto findById(Long feedid) {
        Feed findFeed = feedRepository.findByOrElseThrow(feedid);
        User user = findFeed.getUser();
        Category category = findFeed.getCategory();

        FeedResponseDto feedResponseDto = new FeedResponseDto(
                findFeed.getId(),
                category.getName(),
                user.getNickname(),
                findFeed.getTitle(),
                findFeed.getDetail(),
                findFeed.getCreatedAt(),
                findFeed.getUpdatedAt()
        );

        // 좋아요 수 조회
        int likeCount = likeRepository.countByFeed_IdAndLikeStatus(feedid, true);
        feedResponseDto.setLikeCount(likeCount);

        return feedResponseDto;
    }


    // 피드 삭제
    public void deleteFeed(Long feedid, Long loginUserId) {
        Feed findFeed = feedRepository.findByOrElseThrow(feedid);
        Long feedUserId = findFeed.getUser().getId();
        User findUser = userRepository.findByIdOrElseThrow(loginUserId);


        if(Objects.equals(feedUserId, findUser.getId() )) {
            feedRepository.delete(findFeed);
        } else {
            throw new CustomException(UNAUTHORIZED_USER);
        }

    }

    // 피드 생성
    public FeedResponseDto save(Long userId, Long categoryid, String title, String contents) {

        User findUser = userRepository.findByIdOrElseThrow(userId);
        Category category = categoryRepository.findByIdOrElseThrow(categoryid);


        Feed feed = new Feed(title, contents);
        feed.setUser(findUser);
        feed.setCategory(category);

        Feed savedFeed = feedRepository.save(feed);

        User nickname = feed.getUser();

        return new FeedResponseDto(savedFeed.getId(), category.getName() ,nickname.getNickname(), savedFeed.getTitle(),
                savedFeed.getDetail(),savedFeed.getCreatedAt(), savedFeed.getUpdatedAt());
    }


    // 피드 수정
    @Transactional
    public void updateFeed(Long feedid, Long loginUserId, String title, String contents) {

        Feed findFeed = feedRepository.findByOrElseThrow(feedid);
        Long feedUserId = findFeed.getUser().getId();
        //요청유저가 작성자인지 판별
        User findUser = userRepository.findByIdOrElseThrow(loginUserId);

        if(!Objects.equals(feedUserId, findUser.getId())) {
            throw new CustomException(UNAUTHORIZED_USER);
        }

        if(findFeed.getId() == null) {
            throw new CustomException(ID_NOT_FOUND);
        }

        if(title == null || contents == null) {
            throw new CustomException(TITLE_DETAIL_NULL);
        }

        findFeed.updateFeed(title, contents);
    }

    // 페이징 조회 수정일자순
    public List<FeedResponseDto> findFeedByUpdatedPage(Pageable pageable) {

        List<FeedResponseDto> feedResponseDtoList = new ArrayList<>();
        Page<Feed> feedPage = feedRepository.findAll(pageable);
        List<Feed> feedList = feedPage.getContent();
        // Feed에서 User 객체를 제외하고 나머지 필드들만 불러와서 반환.

        for (Feed feed : feedList) {
            //FeedResponseDto에 Feed 필드를 넣는다.
            FeedResponseDto feedResponseDto = new FeedResponseDto(
                    feed.getId(),
                    feed.getCategory().getName(),
                    feed.getUser().getNickname(),
                    feed.getTitle(),
                    feed.getDetail(),
                    feed.getCreatedAt(),
                    feed.getUpdatedAt()
            );

            // 좋아요 수 조회
            int likeCount = likeRepository.countByFeed_IdAndLikeStatus(feed.getId(), true);
            feedResponseDto.setLikeCount(likeCount);

            feedResponseDtoList.add(feedResponseDto);
        }

        return feedResponseDtoList;
//        return feedRepository.findAll(pageable).getContent().stream().map(FeedResponseDto::feedDto).toList();
    }


    // 페이징 조회 좋아요 순
    public List<FeedResponseDto> findFeedByLikeCountPage(Pageable pageable) {

        List<FeedResponseDto> feedResponseDtoList = new ArrayList<>();
        Page<Feed> feedPage = feedRepository.findAll(pageable);
        List<Feed> feedList = feedPage.getContent();
        // Feed에서 User 객체를 제외하고 나머지 필드들만 불러와서 반환.

        for (Feed feed : feedList) {
            //FeedResponseDto에 Feed 필드를 넣는다.
            FeedResponseDto feedResponseDto = new FeedResponseDto(
                    feed.getId(),
                    feed.getCategory().getName(),
                    feed.getUser().getNickname(),
                    feed.getTitle(),
                    feed.getDetail(),
                    feed.getCreatedAt(),
                    feed.getUpdatedAt()
            );

            // 좋아요 수 조회
            int likeCount = likeRepository.countByFeed_IdAndLikeStatus(feed.getId(), true);
            feedResponseDto.setLikeCount(likeCount);

            feedResponseDtoList.add(feedResponseDto);
        }

        // 좋아요 수 기준으로 정렬 (내림차순)
        feedResponseDtoList.sort((a, b) -> Integer.compare(b.getLikeCount(), a.getLikeCount()));

        return feedResponseDtoList;
//        return feedRepository.findAll(pageable).getContent().stream().map(FeedResponseDto::feedDto).toList();
    }


    // 기간별 검색 기능
    public List<FeedResponseDto> findFeedsByDateRange(LocalDate startDate, LocalDate endDate) {

        // LocalDate -> LocalDateTime 으로 변환
        LocalDateTime startDateTime = startDate.atStartOfDay(); // 뒤 시간을 00:00:00 으로 설정
        LocalDateTime endDateTime = endDate.atTime(23,59,59); //뒤 시간을 23:59:59 로 설정

        List<Feed> feeds = feedRepository.findAllByUpdatedAtBetween(startDateTime, endDateTime);

        List<FeedResponseDto> responseDtos = new ArrayList<>();

        for(Feed feed : feeds) {
            FeedResponseDto dto = new FeedResponseDto(
                    feed.getId(),
                    feed.getCategory().getName(),
                    feed.getUser().getNickname(),
                    feed.getTitle(),
                    feed.getDetail(),
                    feed.getCreatedAt(),
                    feed.getUpdatedAt()
            );

            int likeCount = likeRepository.countByFeed_IdAndLikeStatus(feed.getId(), true);
            dto.setLikeCount(likeCount);

            responseDtos.add(dto);
        }
        return responseDtos;
    }

}
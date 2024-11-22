package com.example.todolog.controller;


import com.example.todolog.dto.feeddto.DateRangeRequestDto;
import com.example.todolog.dto.feeddto.FeedRequestDto;
import com.example.todolog.dto.feeddto.FeedResponseDto;
import com.example.todolog.dto.feeddto.FeedUpdateRequestDto;
import com.example.todolog.entity.Feed;
import com.example.todolog.entity.User;
import com.example.todolog.repository.FeedRepository;
import com.example.todolog.service.FeedService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/feeds")
@RestController
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;
    private final FeedRepository feedRepository;

    // 피드 전체 조회
    @GetMapping
    public ResponseEntity<List<FeedResponseDto>> findAll() {
        List<FeedResponseDto> feedResponseDtoList = feedService.findAll();

        return new ResponseEntity<>(feedResponseDtoList, HttpStatus.OK);
    }

    // 페이징 조회, 수정일자순 조회
    @GetMapping("/paging/updatedAt")
    public List<FeedResponseDto> findFeedByUpdatedAtPage(
            @PageableDefault(size = 10, sort = "updatedAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        log.info("sorted = {}", pageable.getSort());
        List<FeedResponseDto> feedByUpdatedPage = feedService.findFeedByUpdatedPage(pageable);

        return feedByUpdatedPage;
    }

    //페이징 조회, 좋아요 많은 순 조회
    @GetMapping("/paging/like")
    public List<FeedResponseDto> findFeedByLikeCountPage(
            @PageableDefault(size = 10) Pageable pageable
    ) {
        log.info("sorted = {}", pageable.getSort());
        List<FeedResponseDto> feedByLikeCountPage = feedService.findFeedByLikeCountPage(pageable);

        return feedByLikeCountPage;
    }

    // 피드 단건 조회
    @GetMapping("/{feedid}")
    public ResponseEntity<FeedResponseDto> findById(@PathVariable Long feedid) {
        FeedResponseDto feedResponseDto = feedService.findById(feedid);

        return new ResponseEntity<>(feedResponseDto, HttpStatus.OK);
    }

    // 피드 삭제
    @DeleteMapping("/{feedid}")
    public ResponseEntity<String> deleteFeed(
            @PathVariable Long feedid,
            HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        //login 되어있는 user data
        User loginUser = (User) session.getAttribute("sessionKey");

        feedService.deleteFeed(feedid, loginUser.getId());
        return ResponseEntity.ok().body("정상적으로 삭제 되었습니다.");
    }

    // 피드 생성
    @PostMapping
    public ResponseEntity<FeedResponseDto> createFeed(@RequestBody FeedRequestDto feedRequestDto,
                                                      HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        //login 되어있는 user data
        User loginUser = (User) session.getAttribute("sessionKey");

        FeedResponseDto feedResponseDto = feedService.save(
                loginUser.getId(),
                feedRequestDto.getCategoryid(),
                feedRequestDto.getTitle(),
                feedRequestDto.getDetail());

        return new ResponseEntity<>(feedResponseDto, HttpStatus.CREATED);

    }

    // 피드 업데이트
    @PatchMapping("/{feedid}")
    public ResponseEntity<FeedResponseDto> updateFeed(
            @RequestBody FeedUpdateRequestDto updateRequestDto,
            @PathVariable Long feedid,
            HttpServletRequest request) {

        Feed findFeed = feedRepository.findByOrElseThrow(feedid);

        HttpSession session = request.getSession(false);
        //login 되어있는 user data
        User loginUser = (User) session.getAttribute("sessionKey");

        feedService.updateFeed(feedid, loginUser.getId() ,updateRequestDto.getTitle(), updateRequestDto.getDetail());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 기간별 피드 검색
    @PostMapping("/searchDate")
    public List<FeedResponseDto> findFeedsByDateRange(@RequestBody DateRangeRequestDto requestDto) {
        return feedService.findFeedsByDateRange(requestDto.getStartDate(), requestDto.getEndDate());
    }
}

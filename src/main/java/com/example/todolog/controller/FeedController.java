package com.example.todolog.controller;


import com.example.todolog.dto.feeddto.FeedRequestDto;
import com.example.todolog.dto.feeddto.FeedResponseDto;
import com.example.todolog.dto.feeddto.FeedUpdateRequestDto;
import com.example.todolog.entity.Feed;
import com.example.todolog.entity.User;
import com.example.todolog.repository.FeedRepository;
import com.example.todolog.service.FeedService;
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
        log.info("sorted ={}", pageable.getSort());
        List<FeedResponseDto> feedByPageRequest = feedService.findFeedByPage(pageable);
        return feedByPageRequest;
    }


    // 피드 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<FeedResponseDto> findById(@PathVariable Long id) {
        FeedResponseDto feedResponseDto = feedService.findById(id);

        return new ResponseEntity<>(feedResponseDto, HttpStatus.OK);
    }

    // 피드 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFeed(
            @PathVariable Long id,
            @RequestBody FeedRequestDto dto) {

        feedService.deleteFeed(id, dto.getNickname());
        return ResponseEntity.ok().body("정상적으로 삭제 되었습니다.");
    }

    // 피드 생성
    @PostMapping
    public ResponseEntity<FeedResponseDto> createFeed(@RequestBody FeedRequestDto feedRequestDto) {

        FeedResponseDto feedResponseDto = feedService.save(
                feedRequestDto.getUserId(),
                feedRequestDto.getTitle(),
                feedRequestDto.getDetail());

        return new ResponseEntity<>(feedResponseDto, HttpStatus.CREATED);

    }

    // 피드 업데이트
    @PatchMapping("/{id}")
    public ResponseEntity<FeedResponseDto> updateFeed(@RequestBody FeedUpdateRequestDto updateRequestDto,
                                                      @PathVariable Long id) {

        Feed findFeed = feedRepository.findByOrElseThrow(id);

        User user = findFeed.getUser();

        feedService.updateFeed(id, updateRequestDto.getNickname() ,updateRequestDto.getTitle(), updateRequestDto.getDetail());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

package com.example.todolog.controller;


import com.example.todolog.dto.feeddto.FeedRequestDto;
import com.example.todolog.dto.feeddto.FeedResponseDto;
import com.example.todolog.dto.feeddto.FeedUpdateRequestDto;
import com.example.todolog.repository.UserRepository;
import com.example.todolog.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/feeds")
@RestController
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;
    private final UserRepository userRepository;

    // 피드 전체 조회
    @GetMapping
    public ResponseEntity<List<FeedResponseDto>> findAll() {
        List<FeedResponseDto> feedResponseDtoList = feedService.findAll();

        return new ResponseEntity<>(feedResponseDtoList, HttpStatus.OK);
    }

    // 피드 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<FeedResponseDto> findById(@PathVariable Long id) {
        FeedResponseDto feedResponseDto = feedService.findById(id);

        return new ResponseEntity<>(feedResponseDto, HttpStatus.OK);
    }

    // 피드 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFeed(@PathVariable Long id) {
        feedService.deleteFeed(id);
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
        feedService.updateFeed(id, updateRequestDto.getTitle(), updateRequestDto.getDetail());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

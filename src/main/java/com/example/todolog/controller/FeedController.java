package com.example.todolog.controller;


import com.example.todolog.dto.feeddto.FeedRequestDto;
import com.example.todolog.dto.feeddto.FeedResponseDto;
import com.example.todolog.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @GetMapping("/feeds")
    public ResponseEntity<List<FeedResponseDto>> findAll() {
        return ResponseEntity.ok().body(feedService.findAll());
    }

    @GetMapping("/feeds/{id}")
    public ResponseEntity<FeedResponseDto> findOne(@PathVariable Long id) {
        return ResponseEntity.ok().body(feedService.findById(id));
    }
}

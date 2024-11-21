package com.example.todolog.controller;

import com.example.todolog.dto.comment.CreateCommentRequestDto;
import com.example.todolog.dto.like.LikeCommentRequestDto;
import com.example.todolog.dto.like.LikeCommentResponseDto;
import com.example.todolog.dto.like.LikeFeedRequestDto;
import com.example.todolog.dto.like.LikeFeedResponseDto;
import com.example.todolog.service.LikeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/feed")
    public ResponseEntity<?> likeFeed(
            @Validated @RequestBody LikeFeedRequestDto requestDto,
            BindingResult bindingResult){
        ResponseEntity<?> errorMap = getResponseEntity(bindingResult);
        if (errorMap != null) return errorMap;


        LikeFeedResponseDto likeFeedResponseDto = likeService.likeFeed(requestDto.getFeedId() , requestDto.getUserId());

        return new ResponseEntity<>(likeFeedResponseDto, HttpStatus.OK);
    }

    @PostMapping("/comment")
    public ResponseEntity<?> likeComment(
            @Validated @RequestBody LikeCommentRequestDto requestDto,
            BindingResult bindingResult){
        ResponseEntity<?> errorMap = getResponseEntity(bindingResult);
        if (errorMap != null) return errorMap;

        LikeCommentResponseDto likeCommentResponseDto = likeService.likeComment(requestDto.getCommentId(),requestDto.getUserId());

        return new ResponseEntity<>(likeCommentResponseDto,HttpStatus.OK);
    }

    //validation 예외처리 error
    static ResponseEntity<?> getResponseEntity(BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();

            for(FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }

            return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}

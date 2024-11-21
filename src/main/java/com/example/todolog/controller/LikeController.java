package com.example.todolog.controller;

import com.example.todolog.dto.comment.CreateCommentRequestDto;
import com.example.todolog.dto.like.LikeCommentRequestDto;
import com.example.todolog.dto.like.LikeCommentResponseDto;
import com.example.todolog.dto.like.LikeFeedRequestDto;
import com.example.todolog.dto.like.LikeFeedResponseDto;
import com.example.todolog.entity.User;
import com.example.todolog.service.LikeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/feed/{feedId}")
    public ResponseEntity<?> likeFeed(
            @PathVariable Long feedId ,
            HttpServletRequest request){
        //session loginUser data
        HttpSession session = request.getSession(false);
        User loginUser = (User) session.getAttribute("sessionKey");

        LikeFeedResponseDto likeFeedResponseDto = likeService.likeFeed(feedId, loginUser.getId());

        return new ResponseEntity<>(likeFeedResponseDto, HttpStatus.OK);
    }

    @PostMapping("/comment/{commentId}")
    public ResponseEntity<?> likeComment(
            @PathVariable Long commentId,
            HttpServletRequest request){
        //session loginUser data
        HttpSession session = request.getSession(false);
        User loginUser = (User) session.getAttribute("sessionKey");

        LikeCommentResponseDto likeCommentResponseDto = likeService.likeComment(commentId,loginUser.getId());

        return new ResponseEntity<>(likeCommentResponseDto,HttpStatus.OK);
    }

    @GetMapping("feed/{feedId}")
    public ResponseEntity<Integer> likeCountByFeedId (@PathVariable Long feedId){
        int countLike = likeService.likeCountByFeedId(feedId);
        return new ResponseEntity<>(countLike,HttpStatus.OK);
    }

    @GetMapping("comment/{commentId}")
    public ResponseEntity<Integer> likeCountByCommentId (@PathVariable Long commentId){
        int countLike = likeService.likeCountByCommentId(commentId);
        return new ResponseEntity<>(countLike,HttpStatus.OK);
    }






    //validation 예외처리 error
    static ResponseEntity<?> getResponseEntity(BindingResult bindingResult) {
        ResponseEntity<?> errorMap = CategoryController.getResponseEntity(bindingResult);
        if (errorMap != null) return errorMap;
        return null;
    }
}

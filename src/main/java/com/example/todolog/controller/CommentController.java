package com.example.todolog.controller;


import com.example.todolog.common.Const;
import com.example.todolog.dto.UserResponseDto;
import com.example.todolog.dto.comment.CommentResponseDto;
import com.example.todolog.dto.comment.CreateCommentRequestDto;
import com.example.todolog.entity.User;
import com.example.todolog.service.CommentService;
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
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService ;

    @PostMapping
    public ResponseEntity<?> save(
            @Validated @RequestBody CreateCommentRequestDto requestDto,
            BindingResult bindingResult,
            HttpServletRequest request){

        //validation 예외 처리
        ResponseEntity<?> errorMap = LikeController.getResponseEntity(bindingResult);
        if (errorMap != null) return errorMap;

        HttpSession session = request.getSession(false);
        //login 되어있는 user data
        //User loginUser = (User) session.getAttribute(Const.LOGIN_USER);

        CommentResponseDto commentResponseDto = commentService.save(requestDto.getFeedId() , 1L , requestDto.getDetail());

        return new ResponseEntity<>(commentResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseDto> findById(@PathVariable Long id){
        CommentResponseDto commentResponseDto = commentService.findById(id);
        return new ResponseEntity<>(commentResponseDto , HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDetail(
            @PathVariable Long id,
            @RequestBody Map<String, String> detailMap,
            HttpServletRequest request){

        HttpSession session = request.getSession(false);
        //login 되어있는 user data
        User loginUser = (User) session.getAttribute(Const.LOGIN_USER);

        commentService.updateComment(id, loginUser.getId(), detailMap.get("detail"));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,HttpServletRequest request){
        HttpSession session = request.getSession(false);
        //login 되어있는 user data
        User loginUser = (User) session.getAttribute(Const.LOGIN_USER);
        commentService.delete(id,loginUser.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

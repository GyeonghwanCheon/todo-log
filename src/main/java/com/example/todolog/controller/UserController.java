package com.example.todolog.controller;

import com.example.todolog.dto.SignupRequestDto;
import com.example.todolog.dto.SignupResponseDto;
import com.example.todolog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 유저 컨트롤러 클래스
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    // 의존성 주입
    private final UserService userService;

    // 회원가입 메서드
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody SignupRequestDto requestDto){

        // 회원가입 로직 실행
        SignupResponseDto signupResponseDto = userService.signup(requestDto.getNickname(), requestDto.getPassword(), requestDto.getEmail());

        // 회원가입 성공 시 객체 정보 반환 + 201 코드 반환
        return new ResponseEntity<>(signupResponseDto, HttpStatus.CREATED);
    }

}
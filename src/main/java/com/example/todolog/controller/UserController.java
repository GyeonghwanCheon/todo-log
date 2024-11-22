package com.example.todolog.controller;

import com.example.todolog.config.PasswordEncoder;
import com.example.todolog.dto.SignupRequestDto;
import com.example.todolog.dto.SignupResponseDto;
import com.example.todolog.dto.UpdatePasswordRequestDto;
import com.example.todolog.dto.UpdateProfileRequestDto;
import com.example.todolog.dto.UserResponseDto;
import com.example.todolog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<SignupResponseDto> signup(@RequestBody SignupRequestDto requestDto) {

        // 회원가입 로직 실행
        SignupResponseDto signupResponseDto = userService.signup(requestDto.getNickname(), requestDto.getPassword(), requestDto.getEmail());

        // 회원가입 성공 시 객체 정보 반환 + 201 코드 반환
        return new ResponseEntity<>(signupResponseDto, HttpStatus.CREATED);
    }

    // 유저 조회 메서드
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long userId) {

        // 유저 조회 로직 실행
        UserResponseDto userResponseDto = userService.findById(userId);

        // 회원가입 성공 시 객체 정보 반환 + 201 코드 반환
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    // 유저 프로필 수정 메서드
    @PatchMapping("/profile/{userId}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long userId, @RequestBody UpdateProfileRequestDto dto){

        // 새로운 mbti, 상태메세지 정보 넘기기
        userService.updateProfile(userId,dto.getNewMbti(), dto.getNewStatusMs());

        // 유저 프로필 수정 성공시 201 코드 반환
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 유저 비밀번호 수정 메서드
    @PatchMapping("/password/{userId}")
    public ResponseEntity<UserResponseDto> updatePassword(@PathVariable Long userId, @RequestBody UpdatePasswordRequestDto dto) {

        // 이전 비밀번호, 새로운 비밀번호 정보 넘기기
        userService.updatePassword(userId,dto.getOldPassword(),dto.getNewPassword());

        // 유저 비밀번호 수정 성공시 201 코드 반환
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 유저 삭제 메서드
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {

        // 유저 삭제 로직 실행
        userService.deleteUser(userId);

        // 유저 삭제 성공시 201 코드 반환
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

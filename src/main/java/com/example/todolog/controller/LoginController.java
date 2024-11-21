package com.example.todolog.controller;

import com.example.todolog.dto.LoginRequestDto;
import com.example.todolog.entity.User;
import com.example.todolog.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Optional;

// 로그인 컨트롤러
@RestController
@RequiredArgsConstructor
@RequestMapping
public class LoginController {

    // 의존성 주입
    private final UserRepository userRepository;

    // 로그인 요청 처리 메서드
    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletRequest request) {

        // 이메일로 유저를 검색
        Optional<User> findUserByEmail = userRepository.findByEmail(loginRequestDto.getEmail());

        // 요청 받은 이메일이 없을 경우 401 코드 반환
        if (findUserByEmail.isEmpty()) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        // user 객체 가져오기
        User user = findUserByEmail.get();

        // 비밀번호 검증, 불 일치시에 401 코드 반환
        if (!Objects.equals(user.getPassword(), loginRequestDto.getPassword())) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        // 세션 생성
        HttpSession session = request.getSession(true);

        // 사용자 이메일을 세션키로 저장
        session.setAttribute("sessionKey", user.getEmail());

        // 200 코드 반환
        return new ResponseEntity(HttpStatus.OK);
    }

    // 로그아웃 메서드
    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        // 세션이 존재하면 가져오고 없으면 null 반환
        HttpSession session = request.getSession(false);

        // 세선 존재 여부 확인
        if (session != null) {

            // 세션 지우기
            session.invalidate();

            // 200 코드 반환
            return new ResponseEntity(HttpStatus.OK);
        }

        // 세션이 존재하지 않을 시
        else {
            // 400 코드 반환
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}

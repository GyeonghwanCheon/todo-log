package com.example.todolog.service;

import com.example.todolog.dto.SignupResponseDto;
import com.example.todolog.entity.User;
import com.example.todolog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// 유저 비즈니스 로직
@Service
@RequiredArgsConstructor
public class UserService {

    // 의존성 부여
    private final UserRepository userRepository;

    // 회원가입 메서드
    public SignupResponseDto signup(String nickname, String password, String email) {

        // 입력 받은 정보로 객체 생성
        User user = new User(nickname, password, email);

        // 생성된 객체 DB에 저장
        User savedUser = userRepository.save(user);

        // 객체 정보 반환
        return new SignupResponseDto(savedUser.getId(), savedUser.getNickname(), savedUser.getEmail());
    }
}

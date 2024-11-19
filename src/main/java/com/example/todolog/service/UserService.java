package com.example.todolog.service;

import com.example.todolog.dto.UserResponseDto;
import com.example.todolog.dto.SignupResponseDto;
import com.example.todolog.entity.User;
import com.example.todolog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

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

    // 유저 조회 메서드
    public UserResponseDto findByNickname(String nickname) {

        // 닉네임으로 유저 검색하여 Optional 객체에 대입
        Optional<User> optionalUser = userRepository.findByNickname(nickname);

        // 없는 유저 입력시 404 코드 반환
        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        // Optional 객체에서 유저 데이터 꺼내기
        User findUser = optionalUser.get();

        // 객체 정보 반환
        return new UserResponseDto(findUser.getNickname(), findUser.getEmail(), findUser.getMbti(), findUser.getStatusMs());
    }
}

package com.example.todolog.service;

import com.example.todolog.config.PasswordEncoder;
import com.example.todolog.dto.UserResponseDto;
import com.example.todolog.dto.SignupResponseDto;
import com.example.todolog.entity.User;
import com.example.todolog.error.errorcode.ErrorCode;
import com.example.todolog.error.exception.CustomException;
import com.example.todolog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

// 유저 비즈니스 로직
@Service
@RequiredArgsConstructor
public class UserService {

    // 의존성 부여
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입시 기존 유저 확인 메서드
    private void checkAlreadySignup(String email, String password) {

        User user = userRepository.findByEmail(email).orElse(null);

        if (user != null) {
            checkBan(user);
            throw new CustomException(ErrorCode.DUPLICATE_RESOURCE);
        }

        if (!isValidPassword(password)){
            throw new CustomException(ErrorCode.INVALID_PASSWORD_FORMAT);
        }
    }

    // 유저 ID 검증 메서드
    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    // 탈퇴한 유저인지 검증하는 메서드
    private void checkBan(User user) {
        if(user.isBanned()){
            throw new CustomException(ErrorCode.BANED_EMAIL);
        }
    }

    // 비밀번호 형식 검증 메서드
    private boolean isValidPassword(String password) {
        // 최소 8자, 대소문자 영문, 숫자, 특수문자를 각각 최소 1개 이상 포함
        String validPassword = "^(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$";
        return password.matches(validPassword);
    }

    // 회원가입 메서드
    public SignupResponseDto signup(String nickname, String password, String email) {

        checkAlreadySignup(email,password);

        String encodedPassword = passwordEncoder.encode(password);

        User user = new User(nickname, encodedPassword, email);

        User savedUser = userRepository.save(user);

        return new SignupResponseDto(savedUser.getId(), savedUser.getNickname(), savedUser.getEmail());
    }

    // 유저 조회 메서드
    public UserResponseDto findById(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USERNAME_NOT_FOUND));

        checkBan(user);

        return new UserResponseDto(user.getNickname(), user.getEmail(), user.getMbti(), user.getStatusMs());
    }

    // 유저 프로필 수정 메서드
    @Transactional
    public void updateProfile(Long userId, String newMbti, String newStatusMs) {

        User user = findUser(userId);

        checkBan(user);

        user.updateProfile(newMbti, newStatusMs);
    }

    // 유저 비밀번호 수정 메서드
    @Transactional
    public void updatePassword(Long userId, String oldPassword, String newPassword) {

        User user = findUser(userId);

        if(!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_PASSWORD);
        }

        if (!isValidPassword(newPassword)){
            throw new CustomException(ErrorCode.INVALID_PASSWORD_FORMAT);
        }

        if(Objects.equals(oldPassword, newPassword)) {
            throw new CustomException(ErrorCode.DUPLICATE_RESOURCE);
        }

        user.updatePassword(passwordEncoder.encode(newPassword));
    }

    // 유저 삭제 메서드
    @Transactional
    public void deleteUser(Long userId) {

        User user = findUser(userId);

        checkBan(user);

        user.setBanned(true);
    }
}

package com.example.todolog.service;

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
import java.util.Optional;

// 유저 비즈니스 로직
@Service
@RequiredArgsConstructor
public class UserService {

    // 의존성 부여
    private final UserRepository userRepository;

    // 회원가입 메서드
    public SignupResponseDto signup(String nickname, String password, String email) {

        // 기존 유저 확인
        checkAlreadySignup(email);

        // 입력 받은 정보로 객체 생성
        User user = new User(nickname, password, email);

        // 생성된 객체 DB에 저장
        User savedUser = userRepository.save(user);

        // 객체 정보 반환
        return new SignupResponseDto(savedUser.getId(), savedUser.getNickname(), savedUser.getEmail());
    }

    // 기존 유저 확인 메서드
    private void checkAlreadySignup(String email) {

        // 유저 데이터 가져오기
        Optional<User> checkUser = userRepository.findByEmail(email);

        // 가져온 데이터가 존재한다면 예외 발생
        if (checkUser.isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_RESOURCE);
        }
    }

    // 유저 조회 메서드
    public UserResponseDto findByNickname(String nickname) {

        // 닉네임으로 유저 검색하여 Optional 객체에 대입
        Optional<User> optionalUser = userRepository.findByNickname(nickname);

        // 없는 유저 입력시 404 코드 반환
        if (optionalUser.isEmpty()) {
            throw new CustomException(ErrorCode.USERNAME_NOT_FOUND);
        }

        // Optional 객체에서 유저 데이터 꺼내기
        User findUser = optionalUser.get();

        // 객체 정보 반환
        return new UserResponseDto(findUser.getNickname(), findUser.getEmail(), findUser.getMbti(), findUser.getStatusMs());
    }

    // 유저 프로필 수정 메서드
    @Transactional
    public void updateProfile(Long id, String newMbti, String newStatusMs) {

        // 요청받은 ID 로 유저 데이터 조회, 없으면 예외 표시
        User finduser = userRepository.findByIdOrElseThrow(id);

        // 요청받은 mbti, 상태메세지 수정
        finduser.updateProfile(newMbti,newStatusMs);
    }

    // 유저 비밀번호 수정 메서드
    public void updatePassword(Long id, String oldPassword, String newPassword) {

        // 요청받은 ID로 유저 데이터 조회, 없으면 예외 표시
        User finduser = userRepository.findByIdOrElseThrow(id);

        // 새 비밀번호 형식 검증
        if (!isValidPassword(newPassword)){
            throw new CustomException(ErrorCode.UNAUTHORIZED_PASSWORD);
        }

        // 현재 비밀번호 검증
        if(!Objects.equals(finduser.getPassword(), oldPassword)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_PASSWORD);
        }

        // 현재 비밀번호와 새 비밀번호의 동일 여부 검증
        if(Objects.equals(oldPassword, newPassword)) {
            throw new CustomException(ErrorCode.DUPLICATE_RESOURCE);
        }

        // 입력한 새 비밀번호 적용
        finduser.updatePassword(newPassword);
    }

    // 비밀번호 형식 검증 메서드
    private boolean isValidPassword(String password) {
        // 최소 8자, 대소문자 영문, 숫자, 특수문자를 각각 최소 1개 이상 포함
        String validPassword = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$";
        return password.matches(validPassword);
    }

    // 유저 삭제 메서드
    public void deleteUser(Long id) {

        // 요청받은 ID로 유저 데이터 조회, 없으면 예외 표시
        User finduser = userRepository.findByIdOrElseThrow(id);

        // 유저 삭제
        userRepository.delete(finduser);
    }
}

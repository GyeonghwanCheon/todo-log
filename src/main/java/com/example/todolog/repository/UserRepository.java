package com.example.todolog.repository;

import com.example.todolog.entity.User;
import com.example.todolog.error.errorcode.ErrorCode;
import com.example.todolog.error.exception.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// 유저 DB
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 닉네임으로 DB에서 유저 데이터 찾기
    Optional<User> findByNickname(String nickname);

    // 이메일으로 DB에서 유저 데이터 찾기
    Optional<User> findByEmail(String email);

    default User findByIdOrElseThrow(Long id){
        return findById(id).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }
}

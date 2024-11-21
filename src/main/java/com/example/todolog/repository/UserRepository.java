package com.example.todolog.repository;

import com.example.todolog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

// 유저 DB
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 닉네임으로 DB에서 유저 데이터 찾기
    Optional<User> findByNickname(String nickname);

    // 요청받은 ID가 DB에 있는지 확인하기
    default User findByIdOrElseThrow(Long id){
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}

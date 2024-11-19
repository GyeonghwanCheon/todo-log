package com.example.todolog.repository;

import com.example.todolog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// 유저 DB
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByNickname(String nickname);

}

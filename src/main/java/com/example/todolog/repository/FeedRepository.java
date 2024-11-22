package com.example.todolog.repository;

import com.example.todolog.entity.Feed;
import com.example.todolog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {

    List<Feed> findByUserInOrderByCreatedAtDesc(List<User> users);

    // 특정 날짜 범위 내의 피드를 조회
    List<Feed> findAllByUpdatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    default Feed findByOrElseThrow(Long id) {
        return findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID를 찾을 수 없습니다.")
        );
    }

}

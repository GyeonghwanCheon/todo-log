package com.example.todolog.repository;

import com.example.todolog.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {

    default Feed findByOrElseThrow(Long id) {
        return findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID를 찾을 수 없습니다.")
        );
    }
}

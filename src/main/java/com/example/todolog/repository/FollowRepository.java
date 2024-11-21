package com.example.todolog.repository;

import com.example.todolog.entity.Follow;
import com.example.todolog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    // 특정 사용자가 팔로우하고 있는 모든 사용자
    List<Follow> findByFollower(User follower);

    // 특정 사용자를 팔로우하고 있는 모든 사용자
    List<Follow> findByFollowing(User following);

    // 팔로워와 팔로잉 관계가 존재하는 지 확인
    Optional<Follow> findByFollowerAndFollowing(User follower, User following);

}



package com.example.todolog.service;

import com.example.todolog.dto.feeddto.FeedResponseDto;
import com.example.todolog.dto.follow.FollowDto;
import com.example.todolog.entity.Feed;
import com.example.todolog.entity.Follow;
import com.example.todolog.entity.User;
import com.example.todolog.error.errorcode.ErrorCode;
import com.example.todolog.error.exception.CustomException;
import com.example.todolog.repository.FeedRepository;
import com.example.todolog.repository.FollowRepository;
import com.example.todolog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final FeedRepository feedRepository;

    // 팔로우 신청
    public void followUser(String followerNickname, String followingNickname) {

        User follower = userRepository.findByNickname(followerNickname).orElseThrow(
                () -> new IllegalArgumentException("팔로워를 찾을 수 없습니다."));
        User following = userRepository.findByNickname(followingNickname).orElseThrow(
                () -> new IllegalArgumentException("팔로잉을 찾을 수 없습니다."));

        // 스스로 팔로우하지 못하게 설정
        if(Objects.equals(followerNickname, followingNickname)) {
            throw new CustomException(ErrorCode.DUPLICATE_RESOURCE);
        }

        // 이미 팔로우 일 때
        if(followRepository.findByFollowerAndFollowing(follower, following).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_RESOURCE);
        }

        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowing(following);

        followRepository.save(follow);
    }

    // 사용자의 팔로잉 목록 조회
    public List<FollowDto> findFollowing(String nickname) {
        User user = userRepository.findByNickname(nickname).orElseThrow(
                () -> new IllegalArgumentException("User not found"));

        return followRepository.findByFollower(user).stream()
                .map(follow -> new FollowDto(
                        follow.getFollowing().getNickname(),
                        follow.getFollowing().getEmail(),
                        follow.getFollowing().getMbti(),
                        follow.getFollowing().getStatusMs()
                )).collect(Collectors.toList());
    }

    // 사용자의 팔로잉 목록 조회
    public List<FollowDto> findFollower(String nickname) {
        User user = userRepository.findByNickname(nickname).orElseThrow(
                () -> new IllegalArgumentException("User not found"));

        return followRepository.findByFollowing(user).stream()
                .map(follow -> new FollowDto(
                        follow.getFollowing().getNickname(),
                        follow.getFollowing().getEmail(),
                        follow.getFollower().getMbti(),
                        follow.getFollower().getStatusMs()
                )).collect(Collectors.toList());
    }

    public void unfollowUser(String followerNickname, String followingNickname) {

        // 팔로워 사용자 조회
        User follower = userRepository.findByNickname(followerNickname).orElseThrow(
                () -> new IllegalArgumentException("팔로우를 찾을 수 없습니다."));

        // 팔로잉 사용자 조회
        User following = userRepository.findByNickname(followingNickname).orElseThrow(
                () -> new IllegalArgumentException("팔로잉을 찾을 수 없습니다."));

        // 스스로 삭제시 에러 발생
        if(Objects.equals(followerNickname, followingNickname)) {
            throw new CustomException(ErrorCode.DUPLICATE_RESOURCE);
        }

        // 객체 조회
        Follow follow = followRepository.findByFollowerAndFollowing(follower, following)
                .orElseThrow(() -> new IllegalArgumentException("Follow relationship not found"));

        followRepository.delete(follow);
    }


    // 사용자 팔로워 최신 게시물 조회
    public List<FeedResponseDto> findFollowingFeeds(String nickname) {
        User user = userRepository.findByNickname(nickname).orElseThrow(
                () -> new CustomException(ErrorCode.DUPLICATE_RESOURCE));

        List<User> followingUsers = followRepository.findByFollower(user)
                .stream()
                .map(Follow::getFollowing)
                .collect(Collectors.toList());

        List<Feed> feeds = feedRepository.findByUserInOrderByCreatedAtDesc(followingUsers);

        return feeds.stream()
                .map(FeedResponseDto::feedDto)
                .collect(Collectors.toList());
    }

}

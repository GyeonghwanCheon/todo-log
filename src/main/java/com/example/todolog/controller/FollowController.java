package com.example.todolog.controller;



import com.example.todolog.dto.follow.FollowDto;
import com.example.todolog.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    // 팔로우하기
    @PostMapping("/{followerNickname}/{followingNickname}")
    public ResponseEntity<String> folloUser(@PathVariable String followerNickname, @PathVariable String followingNickname) {
        followService.followUser(followerNickname, followingNickname);
        return ResponseEntity.ok("팔로우 신청 완료!");
    }

    // 사용자의 팔로잉 목록 조회
    @GetMapping("/following/{nickname}")
    public ResponseEntity<List<FollowDto>> findFollowing(@PathVariable String nickname) {
        return ResponseEntity.ok(followService.findFollowing(nickname));
    }

    //사용자의 팔로워 목록 조회
    @GetMapping("/follower/{nickname}")
    public ResponseEntity<List<FollowDto>> findFollower(@PathVariable String nickname) {
        return ResponseEntity.ok(followService.findFollower(nickname));
    }

    //팔로우 해제 (언팔로우)
    @DeleteMapping("/{followerNickname}/{followingNickname}")
    public ResponseEntity<String> unfollowUser(
            @PathVariable String followerNickname,
            @PathVariable String followingNickname) {
        followService.unfollowUser(followerNickname, followingNickname);
        return ResponseEntity.ok("팔로우 삭제 완료!");
    }

}

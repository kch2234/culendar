package com.teamproject.culendar.controller;

import com.teamproject.culendar.dto.FollowResponseDTO;
import com.teamproject.culendar.repository.MemberRepository;
import com.teamproject.culendar.security.domain.CustomMember;
import com.teamproject.culendar.service.FollowService;
import com.teamproject.culendar.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/follows")
@Slf4j
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    // 팔로우, 팔로워 조회
    @GetMapping("/{memberId}/following")
    public ResponseEntity<FollowResponseDTO> followingList(@PathVariable("memberId") Long memberId){
        log.info("********** FollowController GET /follows/:userid/follow - memberId : {}", memberId);
        FollowResponseDTO followingList = followService.getFollowList(memberId);
        return new ResponseEntity<>(followingList, HttpStatus.OK);
    }
    @GetMapping("/{memberId}/follower")
    public ResponseEntity<FollowResponseDTO> followerList(@PathVariable("memberId") Long memberId){
        log.info("********** FollowController GET /follows/:userid/follower - memberId : {}", memberId);
        FollowResponseDTO followerList = followService.getFollowList(memberId);
        return new ResponseEntity<>(followerList, HttpStatus.OK);
    }

    // 팔로우 처리
    @PostMapping("/follow/{followId}")
    public ResponseEntity<String> follow (@AuthenticationPrincipal CustomMember customMember, @PathVariable("followId") Long followId){
        log.info("********** FollowController POST /members/:id/follow - followId : {}", followId);
        Long memberId = customMember.getMember().getId();
        log.info("********** FollowController POST /members/:id/follow - memberId : {}", memberId);
        followService.follow(memberId, followId);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
    // 언팔로우 처리
    @DeleteMapping("/unfollow/{followId}")
    public ResponseEntity<String> unfollow(@AuthenticationPrincipal CustomMember customMember, @PathVariable Long followId){
        log.info("********** FollowController DELETE /members/:id/follow - memberId : {}", followId);
        Long memberId = customMember.getMember().getId();
        log.info("********** FollowController DELETE /members/:id/follow - memberId : {}", memberId);
        followService.unfollow(memberId, followId);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}

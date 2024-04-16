package com.teamproject.culendar.controller;

import com.teamproject.culendar.dto.FollowFollowing;
import com.teamproject.culendar.dto.FollowerResponseDTO;
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
    private final MemberService memberService;
    private final MemberRepository memberRepository;


    // 팔로우, 팔로워 조회
    @GetMapping("/{memberId}/following")
    public ResponseEntity<FollowFollowing> followingList(@PathVariable("memberId") Long memberId){
        log.info("********** FollowController GET /follows/:userid/follow - memberId : {}", memberId);
        FollowFollowing followingList = followService.getFollowingList(memberId);
        return new ResponseEntity<>(followingList, HttpStatus.OK);
    }
    @GetMapping("/{memberId}/follower")
    public ResponseEntity<FollowerResponseDTO> followerList(@PathVariable("memberId") Long memberId){
        log.info("********** FollowController GET /follows/:userid/follower - memberId : {}", memberId);
        FollowerResponseDTO followerList = followService.getFollowerList(memberId);
        log.info("********** FollowController GET /follows/:userid/follower - followerList : {}", followerList);
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
    @DeleteMapping("/unfollow/{memberId}")
    public ResponseEntity<String> unfollow(@PathVariable Long memberId){
        log.info("********** FollowController DELETE /members/:id/follow - memberId : {}", memberId);
        followService.unfollow(memberId);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}

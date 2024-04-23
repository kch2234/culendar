package com.teamproject.culendar.controller;

import com.teamproject.culendar.dto.FollowDTO;
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

import java.util.List;

@RestController
@RequestMapping("/follows")
@Slf4j
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    // 회원이 팔로우 하고 있는 사람 리스트 조회
    @GetMapping("/{memberId}/following")
    public ResponseEntity<List<FollowDTO>> followingList(@PathVariable("memberId") Long memberId) {
        log.info("********** FollowController GET /follows/:userid/follow - memberId : {}", memberId);
        List<FollowDTO> followingList = followService.getFollowList(memberId);
        log.info("********** FollowController GET /follows/:userid/follow - followingList : {}", followingList);

        return new ResponseEntity<>(followingList, HttpStatus.OK);
    }

    // 회원을 팔로우 하고 있는 팔로워 리스트 조회
    @GetMapping("/{memberId}/follower")
    public ResponseEntity<List<FollowDTO>> followerList(@PathVariable("memberId") Long memberId) {
        log.info("********** FollowController GET /follows/:userid/follow - memberId : {}", memberId);
        List<FollowDTO> followerList = followService.getFollowerList(memberId);
        log.info("********** FollowController GET /follows/:userid/follow - followerList : {}", followerList);

        return new ResponseEntity<>(followerList, HttpStatus.OK);
    }

    // 팔로잉 무한 스크롤을 위한 페이징 처리
    @GetMapping("/{memberId}/following/{start}")
    public ResponseEntity<List<FollowDTO>> followingListWithPaging(@PathVariable("memberId") Long memberId, @PathVariable("start") Long start) {
        log.info("팔로우 리스트 무한 스크롤");
        log.info("********** FollowController GET /follows/:userid/follow - memberId : {}", memberId);
        List<FollowDTO> followListWithPaging = followService.getFollowListWithPaging(memberId, start);
        return new ResponseEntity<>(followListWithPaging, HttpStatus.OK);
    }

    // 팔로워 무한 스크롤을 위한 페이징 처리
    @GetMapping("/{memberId}/follower/{start}")
    public ResponseEntity<List<FollowDTO>> followerListWithPaging(@PathVariable("memberId") Long memberId, @PathVariable("start") Long start) {
        log.info("팔로워 리스트 무한 스크롤");
        log.info("********** FollowController GET /follows/:userid/follow - memberId : {}", memberId);
        List<FollowDTO> followerListWithPaging = followService.getFollowerListWithPaging(memberId, start);
        return new ResponseEntity<>(followerListWithPaging, HttpStatus.OK);
    }

    // 팔로우 여부 확인
    @GetMapping("/checkFollow/{followId}")
    public ResponseEntity<Boolean> checkFollow(@AuthenticationPrincipal CustomMember customMember, @PathVariable("followId") Long followId) {
        log.info("********** FollowController GET /members/:id/follow - followId : {}", followId);
        Long memberId = customMember.getMember().getId();
        log.info("********** FollowController GET /members/:id/follow - memberId : {}", memberId);

        Boolean followResult = followService.findById(memberId, followId);
        log.info("********** FollowController GET /members/:id/follow - followResult : {}", followResult);
        if (followResult) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }


    // 팔로우 버튼 클릭 처리
    @PostMapping("/follow/{followId}")
    public ResponseEntity<String> follow(@AuthenticationPrincipal CustomMember customMember, @PathVariable("followId") Long followId) {
        log.info("********** FollowController POST /members/:id/follow - followId : {}", followId);
        Long memberId = customMember.getMember().getId();
        log.info("********** FollowController POST /members/:id/follow - memberId : {}", memberId);

        Boolean followResult = followService.clickFollow(memberId, followId);

        if (followResult) {
            return new ResponseEntity<>("unfollow", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("follow", HttpStatus.OK);
        }

    }
}

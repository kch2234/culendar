package com.teamproject.culendar.controller;

import com.teamproject.culendar.domain.member.Follow;
import com.teamproject.culendar.domain.member.Member;
import com.teamproject.culendar.dto.FollowDTO;
import com.teamproject.culendar.dto.FollowForm;
import com.teamproject.culendar.dto.FollowResponseDTO;
import com.teamproject.culendar.dto.MemberDTO;
import com.teamproject.culendar.repository.MemberRepository;
import com.teamproject.culendar.security.domain.CustomMember;
import com.teamproject.culendar.service.FollowService;
import com.teamproject.culendar.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/follows")
@Slf4j
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;
    private final MemberRepository memberRepository;


    // 팔로우, 팔로워 조회
    @GetMapping("/{memberId}")
    public ResponseEntity<FollowResponseDTO> followingList(@PathVariable("memberId") Long memberId){
        log.info("********** MemberController GET /members/:userid/follow - userid : {}", memberId);
        FollowResponseDTO followResponseDTO = followService.getFollowList(memberId);
        log.info("********** MemberController GET /members/:userid/follow - followResponseDTO : {}", followResponseDTO);
        return new ResponseEntity<>(followResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/follower/{id}")
    public ResponseEntity<String> followerList(@PathVariable("id") Long id){
        log.info("********** MemberController GET /members/:userid/follow - userid : {}", id);
        List<Follow> followerList = followService.findByFollowId(id);
        List<MemberDTO> followers = new ArrayList<>();
        for (Follow follow : followerList) {
            MemberDTO member = new MemberDTO(follow.getMember());
            followers.add(member);
            log.info("********** MemberController GET /members/:userid/follow - followers : {}", followers);
            log.info("********** MemberController GET /members/:userid/follow - member : {}", member);

        }
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    // 팔로우 처리
    @PostMapping("/follow")
    public ResponseEntity<String> follow (@RequestBody FollowForm followForm){
        log.info("********** MemberController POST /members/:id/follow - followForm : {}", followForm);
        Long followed = followService.follow(followForm);
        log.info("********** MemberController POST /members/:id/follow - followed : {}", followed);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
    // 언팔로우 처리
    @DeleteMapping("/{followId}/unfollow")
    public ResponseEntity<String> unfollow(@PathVariable Long followId, @AuthenticationPrincipal CustomMember customMember){
        Long memberId = customMember.getMember().getId();
        log.info("********** MemberController DELETE /members/:id/follow - id : {}", memberId);
        log.info("********** MemberController DELETE /members/:id/follow - followId : {}", followId);
        FollowDTO followDTO = followService.unfollow(memberId, followId);
        log.info("********** MemberController DELETE /members/:id/follow - followDTO : {}", followDTO);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}

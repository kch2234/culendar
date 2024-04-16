package com.teamproject.culendar.service;

import com.teamproject.culendar.domain.member.Follow;
import com.teamproject.culendar.domain.member.Member;
import com.teamproject.culendar.dto.FollowFollowing;
import com.teamproject.culendar.dto.FollowerResponseDTO;
import com.teamproject.culendar.dto.MemberDTO;
import com.teamproject.culendar.repository.FollowRepository;
import com.teamproject.culendar.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ResponseEntity<String> follow(Long memberId, Long followId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        Member follow = memberRepository.findById(followId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        if (memberId.equals(followId)) {
            throw new IllegalArgumentException("자기 자신을 팔로우할 수 없습니다.");
        }
        if (followRepository.findByMemberAndFollow(member, follow).orElse(null) != null) {
            throw new IllegalArgumentException("이미 팔로우한 회원입니다.");
        }
        Follow followEntity = new Follow(member, follow);
        followRepository.save(followEntity);
        return new ResponseEntity<>(member.getUsername() + "님을 팔로우하였습니다.", HttpStatus.OK);
    }
    @Transactional
    public void unfollow(Long memberId) {
        followRepository.deleteById(memberId);
    }


    public Follow findById(Long id) {
        Optional<Follow> followOptional = followRepository.findById(id);
        log.info("FollowService findById : {}", id);
        if (followOptional.isPresent()) {
            return followOptional.get();
        } else {
            return null;
        }
    }

    public FollowFollowing getFollowingList(Long memberId) {
//        Long followingCount = followRepository.countAllByMemberId(memberId);
        List<Follow> followList = followRepository.findByMemberId(memberId);
        List<MemberDTO> listFollowing = new ArrayList<>();
        for (Follow follow : followList) {
            MemberDTO member = new MemberDTO(follow.getFollow());
            listFollowing.add(member);
        }
        log.info("********** FollowService /follows/:userid/follow - followings : {}", listFollowing);
        return new FollowFollowing(listFollowing);
    }

    public FollowerResponseDTO getFollowerList(Long memberId) {
        List<Member> memberList = memberRepository.findAll();
        List<Follow> followerList = followRepository.findByFollowId(memberId);
        log.info("********** FollowService /follows/:userid/follow - followerList : {}", followerList);
        List<MemberDTO> listFollower = new ArrayList<>();
        for (Follow follow : followerList) {
            MemberDTO member = new MemberDTO(follow.getMember());
            listFollower.add(member);
        }
        log.info("********** FollowService /follows/:userid/follow - followers : {}", listFollower);
        return new FollowerResponseDTO(listFollower, memberList);
    }
}

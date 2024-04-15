package com.teamproject.culendar.service;

import com.teamproject.culendar.domain.member.Follow;
import com.teamproject.culendar.domain.member.Member;
import com.teamproject.culendar.dto.FollowDTO;
import com.teamproject.culendar.dto.FollowForm;
import com.teamproject.culendar.dto.FollowResponseDTO;
import com.teamproject.culendar.dto.MemberDTO;
import com.teamproject.culendar.repository.FollowRepository;
import com.teamproject.culendar.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public Long follow(FollowForm followForm) {
        Follow flw = followForm.toEntity();
        Long memberId = followForm.getMember().getId();
        Long followId = followForm.getFollow().getId();
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        Member follow = memberRepository.findById(followId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        if (memberId.equals(followId)) {
            throw new IllegalArgumentException("자기 자신을 팔로우할 수 없습니다.");
        }
        if (followRepository.findByMemberAndFollow(member, follow).orElse(null) != null) {
            throw new IllegalArgumentException("이미 팔로우 중입니다.");
        }
        Follow saved = followRepository.save(flw);
        return saved.getId();
    }

    @Transactional
    public FollowDTO unfollow(Long memberId, Long followId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        Member follow = memberRepository.findById(followId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        followRepository.deleteByMemberAndFollow(member, follow);
        return null;
    }

//    private final MemberRepository memberRepository;
    public Follow findById(Long id) {
        Optional<Follow> followOptional = followRepository.findById(id);
        log.info("FollowService findById : {}", id);
        if (followOptional.isPresent()) {
            return followOptional.get();
        } else {
            return null;
        }
    }

    public List<Follow> findByMemberId(Long id) {
        return followRepository.findByMemberId(id);
    }

    public List<Follow> findByFollowId(Long id) {
        return followRepository.findByFollowId(id);
    }

    public FollowResponseDTO getFollowList(Long memberId) {
//        Long followingCount = followRepository.countAllByMemberId(memberId);
        List<Member> memberList = memberRepository.findAll();
        List<Follow> followList = followRepository.findByMemberId(memberId);
        List<MemberDTO> list = new ArrayList<>();
        for (Follow follow : followList) {
            MemberDTO member = new MemberDTO(follow.getFollow());
            list.add(member);
            log.info("********** MemberController GET /members/:userid/follow - followings : {}", list);
            log.info("********** MemberController GET /members/:userid/follow - member : {}", member);
        }
        return new FollowResponseDTO(memberList, list);
    }
}

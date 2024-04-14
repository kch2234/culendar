package com.teamproject.culendar.service;

import com.teamproject.culendar.domain.member.Follow;
import com.teamproject.culendar.domain.member.Member;
import com.teamproject.culendar.dto.FollowDTO;
import com.teamproject.culendar.repository.FollowRepository;
import com.teamproject.culendar.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public FollowDTO follow(Long memberid, Long followid) {
        Member member = memberRepository.findById(memberid).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        Member follow = memberRepository.findById(followid).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        if (memberid.equals(followid)) {
            throw new IllegalArgumentException("자기 자신을 팔로우할 수 없습니다.");
        }
        if (followRepository.findByMemberAndFollow(member, follow).orElse(null) != null) {
            throw new IllegalArgumentException("이미 팔로우 중입니다.");
        }
        Follow followEntity = new Follow(member, follow);
        Follow saved = followRepository.save(followEntity);
        return new FollowDTO(saved);
    }

    @Transactional
    public FollowDTO unfollow(Long memberid, Long followid) {
        Member member = memberRepository.findById(memberid).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        Member follow = memberRepository.findById(followid).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
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

    /*
    public void saveFollow(Follow followEntity) {
        followRepository.save(followEntity);
    }

    public boolean isFollowing(Member follower, Member following) {
        List<Follow> followList = followRepository.findByMemberId(follower.getId());
        for (Follow follow : followList) {
            if (follow.getFollow().equals(following.getId())) {
                return true;
            }
        }
        return false;
    }*/
}

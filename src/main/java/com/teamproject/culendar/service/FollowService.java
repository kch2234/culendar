package com.teamproject.culendar.service;

import com.teamproject.culendar.domain.member.Follow;
import com.teamproject.culendar.domain.member.Member;
import com.teamproject.culendar.dto.FollowDTO;
import com.teamproject.culendar.dto.FollowResponseDTO;
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

    // 팔로우 여부에 따른 기능 실행
    @Transactional
    public Boolean clickFollow(Long memberId, Long followId) {

        Boolean isFollow = followRepository.findByMemberIdAndFollowId(memberId, followId).isPresent();

        if (isFollow) {

            followRepository.deleteByMemberIdAndFollowId(memberId, followId);
            log.info("********** FollowService findById - unfollow success");

            return true;
        } else {
            Follow follow = new Follow();
            follow.setMember(memberRepository.findById(memberId).orElse(null));
            follow.setFollow(memberRepository.findById(followId).orElse(null));
            followRepository.save(follow);
            log.info("********** FollowService findById - follow success");

            return false;
        }

    }

    // 팔로우 여부 확인
    public Boolean findById(Long memberId, Long followId) {
        Optional<Follow> follow = followRepository.findByMemberIdAndFollowId(memberId, followId);
        log.info("********** FollowService findById - follow : {}", follow);
        if (follow.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    // 팔로우 하고 있는 사람 리스트 조회
    public List<FollowDTO> getFollowList(Long memberId) {

        List<Follow> findFollowingList = followRepository.findAllByMemberId(memberId);
        List<FollowDTO> followingList = new ArrayList<>();
        for (Follow follow : findFollowingList) {
            followingList.add(new FollowDTO(follow));
        }
        log.info("********** FollowService getFollowList - followingList : {}", followingList);

        return followingList;
    }

    // 팔로워 리스트 조회
    public List<FollowDTO> getFollowerList(Long memberId) {

        List<Follow> findFollowerList = followRepository.findAllByFollowId(memberId);
        List<FollowDTO> followerList = new ArrayList<>();
        for (Follow follow : findFollowerList) {
            followerList.add(new FollowDTO(follow));
        }
        log.info("********** FollowService getFollowerList - followerList : {}", followerList);

        return followerList;
    }
}

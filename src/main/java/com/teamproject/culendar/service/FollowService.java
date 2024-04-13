package com.teamproject.culendar.service;

import com.teamproject.culendar.domain.member.Follow;
import com.teamproject.culendar.domain.member.Member;
import com.teamproject.culendar.dto.FollowDTO;
import com.teamproject.culendar.repository.FollowRepository;
import com.teamproject.culendar.repository.MemberRepository;
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
}

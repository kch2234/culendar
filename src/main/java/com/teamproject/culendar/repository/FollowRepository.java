package com.teamproject.culendar.repository;

import com.teamproject.culendar.domain.member.Follow;
import com.teamproject.culendar.domain.member.Member;
import com.teamproject.culendar.dto.FollowDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    void deleteByMemberAndFollow(Member member, Member follow);

    List<Follow> findByMemberId(Long id);
    List<Follow> findByFollowId(Long id);

    Optional<Object> findByMemberAndFollow(Member member, Member follow);

    Long countAllByMemberId(Long memberId);
}

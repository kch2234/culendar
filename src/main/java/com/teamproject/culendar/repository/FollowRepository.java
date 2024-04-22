package com.teamproject.culendar.repository;

import com.teamproject.culendar.domain.member.Follow;
import com.teamproject.culendar.domain.member.Member;
import com.teamproject.culendar.dto.FollowDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    List<Follow> findByMemberId(Long id);
    List<Follow> findByFollowId(Long id);

    Optional<Follow> findByMemberAndFollow(Member member, Member follow);

    @Query("SELECT COUNT(*) " +
            "FROM Follow f " +
            "WHERE f.member.id = :memberId")
    Long FollowsCount(Long memberId);

    @Query("SELECT COUNT(*) " +
            "FROM Follow f " +
            "WHERE f.member.id = :memberId " +
            "AND f.follow.id = :followId")
    Long FollowsState (Long memberId, Long followId);
}

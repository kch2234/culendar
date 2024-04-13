package com.teamproject.culendar.repository;

import com.teamproject.culendar.domain.member.Follow;
import com.teamproject.culendar.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    List<Follow> findByMemberId(Long id);

    long countByFollowId(Long id);

    long countByMemberId(Long id);
}

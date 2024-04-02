package com.teamproject.culendar.repository;

import com.teamproject.culendar.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByUserid(String userid);
}

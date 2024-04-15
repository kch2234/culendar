package com.teamproject.culendar.repository;

import com.teamproject.culendar.domain.member.Interest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterestRepository extends JpaRepository<Interest, Long> {

    List<Interest> findByMemberId(Long updateId);
}

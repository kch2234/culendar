package com.teamproject.culendar.repository;

import com.teamproject.culendar.domain.member.Follow;
import com.teamproject.culendar.domain.member.Member;
import com.teamproject.culendar.dto.FollowDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    // 회원이 팔로우 하고 있는 리스트 조회
    List<Follow> findAllByMemberId(Long memberId);

    // 무한 스크롤을 위한 페이징 처리
    @Query(value = "SELECT * FROM follow WHERE member_id = ?1 ORDER BY create_date DESC LIMIT ?2, 20", nativeQuery = true)
    List<Follow> findAllByMemberIdWtihPaging(Long memberId, Long start);

    // 회원을 팔로우 하고 있는 팔로워 리스트 조회
    List<Follow> findAllByFollowId(Long followId);

    // 팔로우 여부 확인
    Optional<Follow> findByMemberIdAndFollowId(Long memberId, Long followId);

    // 팔로우 취소
    void deleteByMemberIdAndFollowId(Long memberId, Long followId);
}

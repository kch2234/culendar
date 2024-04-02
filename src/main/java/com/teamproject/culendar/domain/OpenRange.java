package com.teamproject.culendar.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class OpenRange {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 전체공개, 회원, 친구공개, 비공개
    // 팔로우 목록 공개범위
    private OpenRangeType followOpenRange;
    // 후기 공개범위
    private OpenRangeType reviewOpenRange;
    // 관람 작품 공개범위
    private OpenRangeType watchedOpenRange;
    // 신청한 모임 일정 공개범위
    private OpenRangeType scheduleOpenRange;
    // 북마크한 작품 공개범위
    private OpenRangeType bookmarkOpenRange;



}

package com.teamproject.culendar.domain.member;

import com.teamproject.culendar.domain.enumfiles.OpenRangeType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class OpenRange {
    // *****    공개 범위    *****

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 전체공개, 회원, 친구공개, 비공개
    // 팔로우 목록 공개범위
    private OpenRangeType followOpenRange = OpenRangeType.ALL;
    // 후기 공개범위
    private OpenRangeType reviewOpenRange = OpenRangeType.ALL;
    // 관람 작품 공개범위
    private OpenRangeType watchedOpenRange = OpenRangeType.ALL;
    // 신청한 모임 일정 공개범위
    private OpenRangeType scheduleOpenRange = OpenRangeType.ALL;
    // 북마크한 작품 공개범위
    private OpenRangeType bookmarkOpenRange = OpenRangeType.ALL;



}

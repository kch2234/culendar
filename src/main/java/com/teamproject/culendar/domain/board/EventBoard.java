package com.teamproject.culendar.domain.board;

import com.teamproject.culendar.domain.baseEntity.BaseEntityLastModifiedDate;
import com.teamproject.culendar.domain.enumFiles.Gender;
import com.teamproject.culendar.domain.member.Member;
import com.teamproject.culendar.domain.program.Program;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class EventBoard extends BaseEntityLastModifiedDate {
    // *****    모임 일정 게시글    *****

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO 썸네일 추가
//    @Column(nullable = false)
    // private String thumbnail;

    @Column(nullable = false)
    private String title;

    @Column(length = 4000)
    private String content;

    // TODO 이미지 추가
    // private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id")
    private Program program;

    // 모임 일정 날짜
    @Column(nullable = false)
    private LocalDateTime eventDate;

    // 최대 인원
    @Column(nullable = false)
    private Integer maxPeople;

    // TODO 지역 추가
//    @Column(nullable = false)
    // private Location location;

    // 필터링 항목
    private Gender filterGender = Gender.ALL;
    private Integer filterMinAge = 0;
    private Integer filterMaxAge = 0;
    // TODO 필터 지역 추가
    // private Location filterLocation;

    // 모임 인원 모집 마감 날짜
    @Column(nullable = false)
    private LocalDateTime deadlineDate;
    // 자동 수락 여부
    private Boolean autoAccept = true;

    // NOTE add 조회수
    private Long viewCount = 0L;

}

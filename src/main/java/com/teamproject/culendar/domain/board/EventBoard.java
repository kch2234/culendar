package com.teamproject.culendar.domain.board;

import com.teamproject.culendar.domain.baseEntity.BaseEntityLastModifiedDate;
import com.teamproject.culendar.domain.enumfiles.Gender;
import com.teamproject.culendar.domain.member.Member;
import com.teamproject.culendar.domain.program.Program;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class EventBoard extends BaseEntityLastModifiedDate {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO 썸네일 추가

    private String title;

    @Column(length = 4000)
    private String content;

    // TODO 이미지 추가

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id")
    private Program program;

    // 모임 일정 날짜
    private LocalDateTime eventDate;

    // 최대 인원
    private Integer maxPeople;

    // TODO 지역 추가
    // private Location location;

    // 필터링 항목
    private Gender filterGender;
    private Integer filterMinAge;
    private Integer filterMaxAge;
    // TODO 필터 지역 추가
//    private Location filterLocation;

    // 모임 인원 모집 마감 날짜
    private LocalDateTime deadlineDate;
    // 자동 수락 여부
    private Boolean autoAccept;

}

package com.teamproject.culendar.domain.program;

import com.teamproject.culendar.domain.enumFiles.Location;
import com.teamproject.culendar.domain.enumFiles.ProgramType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Program {
    // *****    작품    *****

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 사이트 내 작품 고유 번호
    @Column(nullable = false)
    private Long seq;

    // 제목
    @Column(nullable = false)
    private String title;

    // 작품 시작일, 종료일
    @Column(nullable = false)
    private LocalDateTime startDate;
    @Column(nullable = false)
    private LocalDateTime endDate;

    // 작품 시간
    private String programTime;

    // 작품 분류
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProgramType programType;

    // 작품 썸네일 이미지 추가
     private String thumbnail;

    //작품 지역
    private Location location;

    // 작품 위치 x,y
     private Double locationX;
     private Double locationY;
}

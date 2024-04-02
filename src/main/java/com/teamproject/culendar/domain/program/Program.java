package com.teamproject.culendar.domain.program;

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

    // 제목
    private String title;

    // 작품 시작일, 종료일
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    // TODO 분류 ENUM 만들어서 추가

    // TODO 작품 썸네일 이미지 추가
    // private String thumbnail;

    @Column(length = 4000)
    private String description;

    // 작품 위치 x,y
     private int x;
     private int y;
}

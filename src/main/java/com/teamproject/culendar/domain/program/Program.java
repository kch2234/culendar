package com.teamproject.culendar.domain.program;

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

    // 제목
    private String title;

    // 작품 시작일, 종료일
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    // 작품 분류
    @Column(nullable = false)
    private ProgramType programType;

    // TODO 작품 썸네일 이미지 추가
    // private String thumbnail;

    @Column(length = 4000)
    private String description;

    // 작품 위치 x,y
     private Integer locationX;
     private Integer locationY;
}

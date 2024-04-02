package com.teamproject.culendar.domain.program;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Program {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    // TODO 분류 ENUM 만들어서 추가
    // TODO 작품 이미지 추가

    @Column(length = 4000)
    private String description;

    // TODO 작품 위치 x,y 추가
}

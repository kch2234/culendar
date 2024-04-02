package com.teamproject.culendar.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Member extends BaseEntityCreatedDate {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userid;

    @Column(nullable = false, unique = true)
    private String username;

    private String password;

    private int phone = 0;
    private String email = "";

    // 지역 ENUM 예정
    // 생년 월일
    private LocalDateTime birth;
    // 성향 ENUM 예정
    // 성별 ENUM 예정
    // 프로필 사진 예정

    // 한줄 소개
    private String introduction="";

    // 탈퇴 날짜
    private LocalDateTime disabledDate;
    // 탈퇴 여부
    private boolean disabled = false;

}

package com.teamproject.culendar.domain.member;


import com.teamproject.culendar.domain.enumfiles.Gender;
import com.teamproject.culendar.domain.enumfiles.MemberType;
import com.teamproject.culendar.domain.baseEntity.BaseEntityCreatedDate;
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

    @Column(nullable = false, length = 500)
    private String password;

    private int phone = 0;
    private String email = "";

    //TODO 지역 ENUM 예정
    //private Location location;

    // 생년 월일
    private LocalDateTime birth;

    // 성향 ENUM값 수정 예정
    private MemberType memberType;

    @Column(nullable = false, updatable = false)
    private Gender gender;

    //TODO 프로필 사진 예정

    // 한줄 소개
    @Column(length = 1000)
    private String introduction="";

    // 탈퇴 날짜
    private LocalDateTime disabledDate;

    // 탈퇴 여부
    private boolean disabled = false;

}

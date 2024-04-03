package com.teamproject.culendar.domain.member;


import com.teamproject.culendar.domain.enumFiles.Gender;
import com.teamproject.culendar.domain.enumFiles.MemberType;
import com.teamproject.culendar.domain.baseEntity.BaseEntityCreatedDate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Member extends BaseEntityCreatedDate {
    // *****    회원    *****

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 아이디
    @Column(nullable = false, unique = true)
    private String userid;

    // 닉네임
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, length = 500)
    private String password;

    private int phone = 0;
    private String email = "";

    //TODO 지역 ENUM 예정
    //@Column(nullable = false)
    //private Location location;

    // 생년 월일
    @Column(nullable = false)
    private LocalDateTime birth;

    // 성향 ENUM값 수정 예정
    private MemberType memberType;

    @Column(nullable = false, updatable = false)
    private Gender gender;

    //TODO 프로필 사진 예정
    //private String profileImage;

    // 한줄 소개
    @Column(length = 1000)
    private String introduction="";

    // 탈퇴 날짜
    private LocalDateTime disabledDate;

    // 탈퇴 여부
    private boolean disabled = false;

}

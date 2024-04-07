package com.teamproject.culendar.dto;


import com.teamproject.culendar.domain.enumFiles.Gender;
import com.teamproject.culendar.domain.enumFiles.Location;
import com.teamproject.culendar.domain.enumFiles.MemberType;
import com.teamproject.culendar.domain.enumFiles.Role;
import com.teamproject.culendar.domain.member.Member;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class MemberForm  {
    private Long id; // 생략
    private String userid; // 아이디
    private String username; //닉네임
    private String password;
    private int phone;
    private String email;
    //TODO 지역 ENUM 예정
    private Location location;
    // 생년 월일
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private String birth;
    private Gender gender;
    //TODO 프로필 사진 예정
    //private String profileImage;
    // 한줄 소개
    private String introduction;
    // 권한
    private Role role = Role.MEMBER;

    // form -> Entity
    public Member toEntity() {
        Member member = new Member();
        member.setUserid(userid); //아이디
        member.setUsername(username); //닉네임
        member.setPassword(password); // 암호화 된것
        member.setPhone(phone);
        member.setEmail(email);
        member.setLocation(location);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        birth += " 00:00:00";
        member.setBirth(LocalDateTime.parse(birth, formatter));
        member.setGender(gender);
        //member.setProfileImage(profileImage);
        member.setIntroduction(introduction);
        member.setRole(role);
        return member;
    }
}
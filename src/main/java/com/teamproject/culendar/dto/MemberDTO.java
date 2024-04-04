package com.teamproject.culendar.dto;

import com.teamproject.culendar.domain.enumFiles.Gender;
import com.teamproject.culendar.domain.enumFiles.MemberType;
import com.teamproject.culendar.domain.enumFiles.Role;
import com.teamproject.culendar.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    private Long id;
    private String userid;
    private String username;
    private String password;
    private int phone;
    private String email;
    //TODO 지역 ENUM 예정
    //private Location location;
    private LocalDateTime birth;
    private MemberType memberType;
    private Gender gender;
    //TODO 프로필 사진 예정
    //private String profileImage;
    private String introduction;
    private LocalDateTime disabledDate;
    private boolean disabled;
    private Role role;
    private LocalDateTime createDate;

    // Entity -> DTO
    public MemberDTO(Member member){
        this.id = member.getId();
        this.userid = member.getUserid();
        this.username = member.getUsername();
        this.password = member.getPassword();
        this.phone = member.getPhone();
        this.email = member.getEmail();
        //this.location = member.getLocation();
        this.birth = member.getBirth();
        this.memberType = member.getMemberType();
        this.gender = member.getGender();
        this.introduction = member.getIntroduction();
        this.disabledDate = member.getDisabledDate();
        this.disabled = member.isDisabled();
        this.role = member.getRole();
        this.createDate = member.getCreateDate();
    }
}

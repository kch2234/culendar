package com.teamproject.culendar.dto;

import com.teamproject.culendar.domain.enumFiles.Gender;
import com.teamproject.culendar.domain.enumFiles.Location;
import com.teamproject.culendar.domain.enumFiles.MemberType;
import com.teamproject.culendar.domain.enumFiles.Role;
import com.teamproject.culendar.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    private Long id;
    private String userid;
    private String username;
    private String password;
    private Long phone;
    //    @Email(message = "이메일 형식이 아닙니다.")
    private String email;
    //지역 ENUM
    private Location location;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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
    public MemberDTO(Member member) {
        this.id = member.getId();
        this.userid = member.getUserid();
        this.username = member.getUsername();
        this.password = member.getPassword();
        this.phone = member.getPhone();
        this.email = member.getEmail();
        this.location = member.getLocation();
        this.birth = member.getBirth();
        this.memberType = member.getMemberType();
        this.gender = member.getGender();
        this.introduction = member.getIntroduction();
        this.disabledDate = member.getDisabledDate();
        this.disabled = member.isDisabled();
        this.role = member.getRole();
        this.createDate = member.getCreateDate();
    }

    public Member toEntity() {
        Member member = new Member();
        member.setId(this.id);
        member.setUsername(this.username);
        member.setPassword(this.password);
        member.setRole(this.role);
        return member;
    }

    // 전화번호를 세 부분으로 나누는 메서드
    public String[] splitPhoneNumber() {
        String phoneString = String.valueOf(this.phone);
        String[] phoneParts = new String[3];
        phoneParts[0] = phoneString.substring(0, 2);
        phoneParts[1] = phoneString.substring(2, 6);
        phoneParts[2] = phoneString.substring(6);
        return phoneParts;
    }

}

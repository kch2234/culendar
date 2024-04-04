package com.teamproject.culendar.dto;

import com.teamproject.culendar.domain.Member;
import lombok.Data;

@Data
public class MemberForm {
    private Long id; // 생략
    private String username;
    private String password;
    private Role role;

    // form -> Entity
    public Member toEntity() {
        Member member = new Member();
        member.setUsername(username);
        member.setPassword(password); // 암호화 된것
        member.setRole(role);
        return member;
    }
}
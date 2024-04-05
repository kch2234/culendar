package com.teamproject.culendar.dto;

import com.teamproject.culendar.domain.member.Member;
import com.teamproject.culendar.domain.enumFiles.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    private Long id;
    private String username;
    private String password;
    private Role role;

    public MemberDTO(Member member){
        this.id = member.getId();
        this.username = member.getUsername();
        this.password = member.getPassword();
        this.role = member.getRole();
    }

    public Member toEntity() {
        Member member = new Member();
        member.setId(this.id);
        member.setUsername(this.username);
        member.setPassword(this.password);
        member.setRole(this.role);
        return member;
    }
}

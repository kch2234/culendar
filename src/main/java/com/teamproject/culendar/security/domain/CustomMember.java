package com.teamproject.culendar.security.domain;

import com.teamproject.culendar.domain.Member;
import com.teamproject.culendar.dto.MemberDTO;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;
import java.util.Collection;

@Getter
public class CustomMember extends User {

    private MemberDTO member;

    public CustomMember(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public CustomMember(Member member) {
        super(member.getUsername(), member.getPassword(),
                Arrays.asList(new SimpleGrantedAuthority(member.getRole().getValue())));
        this.member = new MemberDTO(member);
    }
}

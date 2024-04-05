package com.teamproject.culendar.security.domain;

import com.teamproject.culendar.domain.member.Member;
import com.teamproject.culendar.dto.MemberDTO;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

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
                Arrays.asList(new SimpleGrantedAuthority("ROLE_" + member.getRole().getValue())));
        this.member = new MemberDTO(member);
    }

}

package com.teamproject.culendar.secutiry.domain;

import com.teamproject.culendar.domain.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
public class CustomMember implements UserDetails {

    private Member member;

    public CustomMember(Member member) {
        this.member = member;
    }

    // 권한
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    // 비밀번호
    @Override
    public String getPassword() {
        return member.getPassword();
    }

    // 아이디
    @Override
    public String getUsername() {
        return member.getUserid();
    }

    // 계정 만료 여부
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    // 계정 잠김 여부
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    // 비밀번호 만료 여부
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    // 계정 사용 가능 여부
    @Override
    public boolean isEnabled() {
        return false;
    }
}

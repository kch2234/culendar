package com.teamproject.culendar.secutiry;

import com.teamproject.culendar.domain.Member;
import com.teamproject.culendar.repository.MemberRepository;
import com.teamproject.culendar.secutiry.domain.CustomMember;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUserid(username);
        if (member != null) {
            return new CustomMember(member);
        }
        return null;
    }
}

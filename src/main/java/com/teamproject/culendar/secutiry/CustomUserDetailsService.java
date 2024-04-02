package com.teamproject.culendar.secutiry;

import com.teamproject.culendar.domain.Member;
import com.teamproject.culendar.repository.MemberRepository;
import com.teamproject.culendar.secutiry.domain.CustomMember;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    public CustomUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<Member> member = memberRepository.findByUserid(userId);
        if (member != null) {
            return null;
        }
        return null;
    }
}

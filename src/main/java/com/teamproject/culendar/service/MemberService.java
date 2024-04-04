package com.teamproject.culendar.service;

import com.teamproject.culendar.domain.member.Member;
import com.teamproject.culendar.dto.MemberDTO;
import com.teamproject.culendar.dto.MemberForm;
import com.teamproject.culendar.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Long saveMember(MemberForm memberForm) {
        // 비밀번호 암호화
        memberForm.setPassword(passwordEncoder.encode(memberForm.getPassword()));
        Member saved = memberRepository.save(memberForm.toEntity());
        log.info("********** MemberService saveMember - saved : {}", saved);
        return saved.getId();
    }

}

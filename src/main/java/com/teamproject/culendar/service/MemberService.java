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

    // 회원정보 전체 조회 - 관리자용

    // 회원정보 상세 조회
    public MemberDTO getOneMember(Long id) {
        Member member = memberRepository.findById(id).orElse(null);
        return new MemberDTO(member);
    }
    // 회원정보 수정

    // 회원정보 삭제 - 회원탈퇴 비활성화 추가 예정
    public void deleteMember(Long id) {}

    public Long saveMember(MemberForm memberForm) {
        // 비밀번호 암호화
        memberForm.setPassword(passwordEncoder.encode(memberForm.getPassword()));
        Member saved = memberRepository.save(memberForm.toEntity());
        log.info("********** MemberService saveMember - saved : {}", saved);
        return saved.getId();
    }

}

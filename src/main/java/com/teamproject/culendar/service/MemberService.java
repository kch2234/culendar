package com.teamproject.culendar.service;

import com.teamproject.culendar.domain.member.Member;
import com.teamproject.culendar.dto.*;
import com.teamproject.culendar.repository.MemberRepository;
import com.teamproject.culendar.security.domain.CustomMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.NoSuchElementException;
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
    public MemberDTO findById(Long id) {
        Member member = memberRepository.findById(id).orElse(null);
        return new MemberDTO(member);
    }
    // 아이디 중복 체크
    public CustomMember findByUserid(String userid) {
        Optional<Member> member = memberRepository.findByUserid(userid);
        if(member.isPresent()) {
            return new CustomMember(member.get());
        }
        return null;
    }
    // 닉네임 중복 체크
    public CustomMember findByUsername(String username) {
        Optional<Member> member = memberRepository.findByUsername(username);
        if(member.isPresent()) {
            return new CustomMember(member.get());
        }
        return null;
    }
    // 이메일 중복 체크
    public CustomMember findByEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if(member.isPresent()) {
            return new CustomMember(member.get());
        }
        return null;
    }

    // 팔로우, 팔로워 조회
    public void findFollow(Long id) {

    }

    // 회원정보 수정
    public Long updateMember(MemberForm memberForm) {
        Member findMember = memberRepository.findById(memberForm.getId()).orElse(null);
        findMember.setUsername(memberForm.getUsername());
        findMember.setLocation(memberForm.getLocation());
        findMember.setIntroduction(memberForm.getIntroduction());
        return findMember.getId();
    }

    // 회원정보 삭제 - 회원탈퇴 비활성화
    public void deactivateMember(Long id) {
        Member findMember = memberRepository.findById(id).orElse(null);
        findMember.setDisabled(true);
        findMember.setDisabledDate(null);
    }

    public Long saveMember(MemberForm memberForm) {
        // 비밀번호 암호화
        memberForm.setPassword(passwordEncoder.encode(memberForm.getPassword()));
        Member saved = memberRepository.save(memberForm.toEntity());
        log.info("********** MemberService saveMember - saved : {}", saved);
        return saved.getId();
    }
}

package com.teamproject.culendar.service;

import com.teamproject.culendar.domain.enumFiles.ProgramType;
import com.teamproject.culendar.domain.member.Interest;
import com.teamproject.culendar.domain.member.Member;
import com.teamproject.culendar.dto.*;
import com.teamproject.culendar.repository.FollowRepository;
import com.teamproject.culendar.repository.MemberRepository;
import com.teamproject.culendar.security.domain.CustomMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static java.time.LocalDateTime.now;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원정보 전체 조회 - 관리자용


    // 회원정보 상세 조회
    public MemberDTO findById(Long id) {
        Member member = memberRepository.findById(id).orElse(null);
        log.info("********** MemberService findById - member : {}", member);
        return new MemberDTO(member);
    }

    // 아이디 중복 체크
    public MemberDTO findByUserid(String userid) {
        log.info("********** MemberService findByUserid - userId : {}", userid);
        Optional<Member> member = memberRepository.findByUserid(userid);
        if(member.isPresent()) {
            return new MemberDTO(member.get());
        }
        return null;
    }
    // 닉네임 중복 체크
    public MemberDTO findByUsername(String username) {
        log.info("********** MemberService findByUsername - username : {}", username);
        Optional<Member> member = memberRepository.findByUsername(username);
        if(member.isPresent()) {
            return new MemberDTO(member.get());
        }
        return null;
    }
    // 이메일 중복 체크
    public MemberDTO findByEmail(String email) {
        log.info("********** MemberService findByEmail - email : {}", email);
        Optional<Member> member = memberRepository.findByEmail(email);
        if(member.isPresent()) {
            return new MemberDTO(member.get());
        }
        return null;
    }

    // 회원정보 수정
    public Long updateMember(MemberForm memberForm) {
        Member findMember = memberRepository.findById(memberForm.getId()).orElse(null);
        findMember.setUsername(memberForm.getUsername());
        findMember.setLocation(memberForm.getLocation());
        findMember.setIntroduction(memberForm.getIntroduction());
        return findMember.getId();
    }

    // 회원 정보 설정


    // 회원정보 삭제 - 회원탈퇴 비활성화
    public void deactivateMember(Long id) {
        Member findMember = memberRepository.findById(id).orElse(null);

        findMember.setDisabled(true);
        findMember.setDisabledDate(now());
    }

    public Long saveMember(MemberForm memberForm) {
        // 비밀번호 암호화
        memberForm.setPassword(passwordEncoder.encode(memberForm.getPassword()));
        Member member = memberForm.toEntity();
        log.info("********** MemberService Test - member : {}", member.getBirth());
        Member saved = memberRepository.save(memberForm.toEntity());
        log.info("********** MemberService saveMember - saved : {}", saved);
        return saved.getId();
    }

    // 전화번호 중복 확인
    public MemberDTO findByPhone(Long phone) {
        log.info("********** MemberService findByPhone - phone : {}", phone);
        Optional<Member> member = memberRepository.findByPhone(phone);
        if(member.isPresent()) {
            return new MemberDTO(member.get());
        }
        return null;
    }

    public Member getMember(Long id) {
        return memberRepository.findById(id).orElse(null);
    }



}

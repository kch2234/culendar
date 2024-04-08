package com.teamproject.culendar.controller;


import com.teamproject.culendar.domain.member.Member;
import com.teamproject.culendar.dto.FollowDTO;
import com.teamproject.culendar.dto.MemberDTO;


import com.teamproject.culendar.dto.MemberForm;
import com.teamproject.culendar.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/members")
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /* 회원 전체 조회 - 관리자용
        (@PreAuthorize("hasRole('ADMIN')"))*/

    // 회원 상세페이지
    @GetMapping("/{id}")
    public String myProfile(@PathVariable("id") Long id, Model model){
        log.info("********** MemberController GET /members/:id (myProfile) - id : {}", id);
        MemberDTO member = memberService.findById(id);
        model.addAttribute("member", member);
        return "profile/memberProfile";
    }
    // 팔로우, 팔로워 조회
    @GetMapping("/{id}/follow")
    public String follow(@PathVariable("id") Long id, Model model){
        log.info("********** MemberController GET /members/:id/follow - id : {}", id);
        return "profile/follow";
    }

    // 회원 정보 수정
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model){
        log.info("********** MemberController GET /members/:id/edit - id : {}", id);
        MemberDTO member = memberService.findById(id);
        model.addAttribute("member", member);
        return "profile/edit";
    }

    // 회원 정보 수정 처리
    @PostMapping("/{id}/edit")
    public String editPro(@PathVariable("id") Long id, MemberForm memberForm){
        log.info("********** MemberController POST /members/:id/edit - id : {}", id);
        log.info("********** MemberController POST /members/:id/edit - memberForm : {}", memberForm);
        memberService.updateMember(memberForm);
        return "redirect:/members/{id}";
    }

    // 회원 탈퇴 - 비활성화 추가 예정


    // 관리자 접근 허용
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String admin() {
        return "member/admin";
    }

    // 회원과 관리자 접근 허용
    @PreAuthorize("hasAnyRole('MEMBER', 'ADMIN')")
    @GetMapping("/member")
    public String member() {
        return "member/member";
    }

}

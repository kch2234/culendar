package com.teamproject.culendar.controller;


import com.teamproject.culendar.dto.MemberDTO;


import com.teamproject.culendar.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;

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
        MemberDTO member = memberService.getOneMember(id);
        model.addAttribute("member", member);
        return "profile/memberProfile";
    }

    // 회원 정보 수정

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

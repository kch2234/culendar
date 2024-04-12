package com.teamproject.culendar.controller;

import com.teamproject.culendar.domain.enumFiles.Gender;
import com.teamproject.culendar.domain.enumFiles.ProgramType;
import com.teamproject.culendar.domain.enumFiles.Role;
import com.teamproject.culendar.domain.member.Interest;
import com.teamproject.culendar.dto.*;
import com.teamproject.culendar.security.domain.CustomMember;
import com.teamproject.culendar.service.InterestService;
import com.teamproject.culendar.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.beans.PropertyEditorSupport;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;
    private final InterestService interestService;

    @GetMapping("/signup")
    public String signupForm(@ModelAttribute MemberForm memberForm, @ModelAttribute InterestForm interestForm, Model model) {
        log.info("********** HomeController GET /signup ");
        // 회원가입 페이지에서 사용할 Role enum 값 전달
        /*model.addAttribute("roleType", Role.values());
        model.addAttribute("genderType", Gender.values());
        model.addAttribute("programType", ProgramType.values());
        model.addAttribute("interestForm", interestForm);*/
        return "member/signup";
    }

    @PostMapping("/signup")
    public String signupPro(InterestForm interestForm, MemberForm memberForm) {
        log.info("********** HomeController POST /signup - memberForm : {}", memberForm);
        log.info("********** HomeController POST /signup - interestList : {}", interestForm.getInterestList());
        // 회원가입
        Long savedId = memberService.saveMember(memberForm); // TODO: savedId 활용 -> 홈에서 모달이나 alert 띄울때 필요하면 사용
        // 회원가입한 회원 조회
        MemberDTO member = memberService.findById(savedId);
        // 회원가입한 회원의 관심분야 저장
        for (ProgramType programType : interestForm.getInterestList()) {
            InterestForm interest = new InterestForm(programType);
            interest.setMember(member.toEntity());
            interestService.saveInterest(interest);
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(@ModelAttribute MemberForm memberForm) {
        log.info("********** HomeController GET /login ");
        return "member/login";
    }
}

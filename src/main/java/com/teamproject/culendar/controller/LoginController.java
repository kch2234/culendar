package com.teamproject.culendar.controller;

import com.teamproject.culendar.domain.enumFiles.Gender;
import com.teamproject.culendar.domain.enumFiles.Role;
import com.teamproject.culendar.dto.MemberForm;
import com.teamproject.culendar.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @GetMapping("/signup")
    public String signupForm(@ModelAttribute("memberForm") MemberForm memberForm, Model model) {
        log.info("********** HomeController GET /signup ");
        // 회원가입 페이지에서 사용할 Role enum 값 전달
        model.addAttribute("roleType", Role.values());
        model.addAttribute("genderType", Gender.values());
        return "member/signup";
    }
    @PostMapping("/signup")
    public String signupPro(MemberForm memberForm) {
        log.info("********** HomeController POST /signup - memberForm : {}", memberForm);
        log.info("********** HomeController POST /signup - memberForm : {}", memberForm.getRole());
        log.info("********** HomeController POST /signup - memberForm : {}", memberForm.getGender());
        Long savedId = memberService.saveMember(memberForm);
        // TODO: savedId 활용 -> 홈에서 모달이나 alert 띄울때 필요하면 사용
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(@ModelAttribute MemberForm memberForm) {
        log.info("********** HomeController GET /login ");
        return "member/login";
    }
}

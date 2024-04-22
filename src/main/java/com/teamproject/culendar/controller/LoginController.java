package com.teamproject.culendar.controller;

import com.teamproject.culendar.domain.enumFiles.Gender;
import com.teamproject.culendar.domain.enumFiles.Location;
import com.teamproject.culendar.domain.enumFiles.ProgramType;
import com.teamproject.culendar.domain.enumFiles.Role;
import com.teamproject.culendar.dto.InterestForm;
import com.teamproject.culendar.dto.MemberDTO;
import com.teamproject.culendar.dto.MemberForm;
import com.teamproject.culendar.domain.member.Interest;
import com.teamproject.culendar.domain.member.Member;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;
    private final InterestService interestService;

    @GetMapping("/signup")
    public String signupForm(@ModelAttribute MemberForm memberForm) {
        log.info("********** HomeController GET /signup ");
        // 회원가입 페이지에서 사용할 Role enum 값 전달

        return "member/signup";
    }

    @PostMapping("/signup")
    public String signupPro(MemberForm memberForm, RedirectAttributes rttr) {
        log.info("********** HomeController POST /signup - memberForm : {}", memberForm);

        log.info("********** HomeController POST /signup - member Birth : {}", memberForm.getBirth());

        Long savedMember = memberService.saveMember(memberForm);
        rttr.addFlashAttribute("result", true);
// 회원가입한 회원의 관심분야 저장
        MemberDTO byId = memberService.findById(savedMember);
        List<ProgramType> interestType = memberForm.getInterestType();
        if (interestType != null) {
            for (ProgramType programType : interestType) {
                InterestForm interestForm = new InterestForm(programType);
                interestForm.setMember(byId.toEntity());
                interestService.saveInterest(interestForm);
            }
        }

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(@ModelAttribute MemberForm memberForm) {
        log.info("********** HomeController GET /login ");
        return "member/login";
    }

    @ModelAttribute("locationType") // 작품 종류 데이터를 뷰에 전달
    public Location[] locations() {
        List<Location> programTypes = new ArrayList<>();
        return Location.values();
    }

    @ModelAttribute("interestType") // 작품 종류 데이터를 뷰에 전달
    public ProgramType[] programTypes() {
        List<ProgramType> programTypes = new ArrayList<>();
        return ProgramType.values();
    }
}

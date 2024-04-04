package com.teamproject.culendar.controller;


import com.teamproject.culendar.security.domain.CustomMember;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String home(@AuthenticationPrincipal CustomMember customMember, Model model) {
        log.info("***** HomeController GET Home!! - customMember : {}", customMember);
        if (customMember == null) { // 로그인 안한 경우
            return "main";
        }
        model.addAttribute("member", customMember); // 로그인한 회원 정보를 Model에 담아서 전달
        return "loginHome";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        log.info("권한이 없는 사용자가 접근했습니다.");
        return "access-denied";
    }

}

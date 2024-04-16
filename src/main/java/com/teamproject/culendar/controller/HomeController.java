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

    // 메인 페이지
    @GetMapping("/")
    // @AuthenticationPrincipal : 현재 로그인한 사용자 정보를 파라미터로 받아올 수 있음
    // CustomMember : 로그인한 사용자 정보를 담고 있는 객체
    public String home(@AuthenticationPrincipal CustomMember customMember, Model model) {
        log.info("***** HomeController GET Home!! - customMember : {}", customMember);
        if (customMember == null) { // 로그인 안한 경우
            return "main";
        }
        model.addAttribute("customMember", customMember); // 로그인한 회원 정보를 Model에 담아서 전달
        return "loginHome";
    }

    @GetMapping("/map")
    public String search() {
        return "contents/map";
    }

    // 권한이 없는 사용자가 접근했을 때
    @GetMapping("/access-denied")
    public String accessDenied(CustomMember customMember) {
        log.info("권한이 없는 사용자가 접근했습니다. : {}", customMember);
        return "access-denied";
    }
}


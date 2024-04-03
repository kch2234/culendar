package com.teamproject.culendar.controller;


import com.teamproject.culendar.security.domain.CustomMember;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String home(@AuthenticationPrincipal CustomMember customMember) {
        log.info("***** HomeController GET Home!! - customMember : {}", customMember);
        if (customMember == null) {
            return "main";
        }
        return "loginHome";
    }

}

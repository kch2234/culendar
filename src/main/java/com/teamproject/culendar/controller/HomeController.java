package com.teamproject.culendar.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class HomeController {

    @Secured("ROLE_ADMIN")
    @GetMapping("/")
    public String home() {
        log.info("********** HomeController GET /home ");
        return "home";
    }
}

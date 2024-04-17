package com.teamproject.culendar.controller;


import com.teamproject.culendar.domain.enumFiles.Location;
import com.teamproject.culendar.domain.enumFiles.ProgramType;
import com.teamproject.culendar.dto.CalendarNameDTO;
import com.teamproject.culendar.dto.MemberDTO;
import com.teamproject.culendar.security.domain.CustomMember;
import com.teamproject.culendar.service.CalendarService;
import com.teamproject.culendar.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final CalendarService calendarService;
    private final MemberService memberService;

    // 메인 페이지
    @GetMapping("/")
    // @AuthenticationPrincipal : 현재 로그인한 사용자 정보를 파라미터로 받아올 수 있음
    // CustomMember : 로그인한 사용자 정보를 담고 있는 객체
    public String home(@AuthenticationPrincipal CustomMember customMember, Model model) {
        log.info("***** HomeController GET Home!! - customMember : {}", customMember);
        if (customMember == null) { // 로그인 안한 경우
        log.info("***** HomeController GET Home - 비로그인 사용자");
            return "main";
        }
        model.addAttribute("customMember", customMember); // 로그인한 회원 정보를 Model에 담아서 전달
        return "main";
    }

    // 권한이 없는 사용자가 접근했을 때
    @GetMapping("/access-denied")
    public String accessDenied(CustomMember customMember) {
        log.info("권한이 없는 사용자가 접근했습니다. : {}", customMember);
        return "access-denied";
    }

    @GetMapping("/myCalendar")
    public String calendar(@AuthenticationPrincipal CustomMember customMember, Model model) {

        log.info("***** HomeController GET /myCalendar - customMember : {}", customMember);
        if (customMember == null) { // 로그인 안한 경우
            return "redirect:/login";
        }
        Long id = customMember.getMember().getId();
        log.info("***** HomeController GET /myCalendar - id : {}", id);

        List<CalendarNameDTO> myCalendarList = calendarService.getCalendarList(id);
        log.info("***** HomeController GET /myCalendar - myCalendarList : {}", myCalendarList);
        if (myCalendarList.size() == 0) {
            calendarService.saveCalendarName(id);
            myCalendarList = calendarService.getCalendarList(id);
            log.info("***** HomeController GET /myCalendar - myCalendarList : {}", myCalendarList);
        }
        MemberDTO byId = memberService.findById(id);
        model.addAttribute("member", byId);

        model.addAttribute("myCalendarList", myCalendarList);


        return "myCalendar/myCalendarHome";
    }


    @ModelAttribute("programType") // 작품 종류 데이터를 뷰에 전달
    public ProgramType[] programTypes() {
        List<ProgramType> programTypes = new ArrayList<>();
        return ProgramType.values();
    }

    @ModelAttribute("locationType") // 작품 종류 데이터를 뷰에 전달
    public Location[] locations() {
        List<Location> programTypes = new ArrayList<>();
        return Location.values();
    }
}


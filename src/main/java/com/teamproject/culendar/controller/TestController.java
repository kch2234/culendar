package com.teamproject.culendar.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class TestController {

  @GetMapping("/")
  public String home() {
    log.info("****** Home");
    return "main";
  }

  @GetMapping("/mycalendar")
  public String myCalendar() {
    log.info("****** myCalendar");
    return "myCalendar/myCalendarHome";
  }

  @GetMapping("/boards/list")
  public String boardModify() {
    log.info("****** myCalendar");
    return "community/boardModify";
  }

}

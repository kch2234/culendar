package com.teamproject.culendar.controller;

import com.teamproject.culendar.dto.ProgramDTO;
import com.teamproject.culendar.security.domain.CustomMember;
import com.teamproject.culendar.service.ProgramService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/program")
public class ProgramController {

    private final ProgramService programService;

    @GetMapping("/{id}")
    public String programDetail(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal CustomMember customMember){

        log.info("** ProgramController GET /program/:id - id: {}", id);
        ProgramDTO programDTO = programService.getOneProgram(id);
        log.info("** ProgramController GET /program/:id - programDTO: {}", programDTO);
        model.addAttribute("program", programDTO);
        model.addAttribute("member", customMember);

        return "program/programDetail";
    }
}

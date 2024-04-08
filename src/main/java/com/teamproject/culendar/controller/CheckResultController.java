package com.teamproject.culendar.controller;

import com.teamproject.culendar.domain.member.Member;
import com.teamproject.culendar.dto.MemberDTO;
import com.teamproject.culendar.security.domain.CustomMember;
import com.teamproject.culendar.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
public class CheckResultController {

    private final MemberService memberService;

    // 회원 아이디 중복 체크
    @PostMapping(value = "/useridCheck")
    public ResponseEntity<String> useridCheck(String userid){
        CustomMember member = memberService.findByUserid(userid);
        String result = "이미 사용 중 입니다";
        if(member == null){
            result = "사용 가능한 아이디 입니다";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 회원 닉네임 중복 체크
    @PostMapping(value = "/usernameCheck")
    public ResponseEntity<String> usernameCheck(String username){
        log.info("********** CheckResultController usernameCheck - username : {}", username);
        CustomMember member = memberService.findByUsername(username);
        String result = "이미 사용 중 입니다";
        if(member == null){
            result = "사용 가능한 닉네임 입니다";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 회원 이메일 중복 체크
    @PostMapping(value = "/emailCheck")
    public ResponseEntity<String> emailCheck(String email){
        log.info("********** CheckResultController emailCheck - email : {}", email);
        CustomMember member = memberService.findByEmail(email);
        String result = "이미 사용 중 입니다";
        if(member == null){
            result = "사용 가능한 이메일 입니다";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

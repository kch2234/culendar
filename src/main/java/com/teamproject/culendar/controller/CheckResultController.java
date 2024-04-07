package com.teamproject.culendar.controller;

import com.teamproject.culendar.domain.member.Member;
import com.teamproject.culendar.dto.MemberDTO;
import com.teamproject.culendar.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
public class CheckResultController {

    private final MemberService memberService;

    // 회원 아이디 중복 체크
    @PostMapping(value = "/useridCheck")
    public ResponseEntity<String> userIdCheck(String userId){
        log.info("********** CheckResultController POST /useridCheck - userId : {}", userId);
        String result = "이미 사용중인 아이디입니다.";
        MemberDTO member = memberService.findByUserid(userId);
        if(member == null){
            result = "사용 가능한 아이디입니다.";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 회원 닉네임 중복 체크
    @PostMapping(value = "/usernameCheck")
    public ResponseEntity<String> usernameCheck(String username){
        log.info("********** CheckResultController POST /usernameCheck - username : {}", username);
        String result = "이미 사용중인 닉네임입니다.";
        MemberDTO member = memberService.findByUsername(username);
        if(member == null){
            result = "사용 가능한 닉네임입니다.";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 회원 이메일 중복 체크
    @PostMapping(value = "/emailCheck")
    public ResponseEntity<String> emailCheck(String email){
        log.info("********** CheckResultController POST /emailCheck - email : {}", email);
        String result = "이미 사용중인 이메일입니다.";
        MemberDTO member = memberService.findByEmail(email);
        if(member == null){
            result = "사용 가능한 이메일입니다.";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

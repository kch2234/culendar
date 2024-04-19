package com.teamproject.culendar.controller;

import com.teamproject.culendar.dto.MemberDTO;
import com.teamproject.culendar.security.domain.CustomMember;
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
    public ResponseEntity<Boolean> useridCheck(String userid){
        MemberDTO memberDTO = memberService.findByUserid(userid);
        log.info("********** CheckResultController useridCheck - memberDTO : {}", memberDTO);
        if (memberDTO != null) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.OK);
    }

    // 회원 닉네임 중복 체크
    @PostMapping(value = "/usernameCheck")
    public ResponseEntity<Boolean> usernameCheck(String username){
        log.info("********** CheckResultController usernameCheck - username : {}", username);
        MemberDTO memberDTO = memberService.findByUsername(username);
        if (memberDTO != null) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.OK);
    }

    // 회원 이메일 중복 체크
    @PostMapping(value = "/emailCheck")
    public ResponseEntity<Boolean> emailCheck(String email){
        log.info("********** CheckResultController emailCheck - email : {}", email);
        MemberDTO memberDTO = memberService.findByEmail(email);
        if (memberDTO != null) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.OK);
    }

    // 회원 전화번호 중복 체크
    @PostMapping(value = "/phoneCheck")
    public ResponseEntity<Boolean> phoneCheck(String phone){
        log.info("********** CheckResultController phoneCheck - phone : {}", phone);
        Long phoneNum = Long.parseLong(phone);
        MemberDTO memberDTO = memberService.findByPhone(phoneNum);
        if (memberDTO != null) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.OK);
    }
}

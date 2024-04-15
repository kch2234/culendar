package com.teamproject.culendar.controller;


import com.teamproject.culendar.domain.enumFiles.ProgramType;
import com.teamproject.culendar.domain.member.Follow;
import com.teamproject.culendar.domain.member.Interest;
import com.teamproject.culendar.domain.member.Member;
import com.teamproject.culendar.dto.FollowDTO;
import com.teamproject.culendar.dto.InterestForm;
import com.teamproject.culendar.dto.MemberDTO;


import com.teamproject.culendar.dto.MemberForm;
import com.teamproject.culendar.repository.MemberRepository;
import com.teamproject.culendar.security.domain.CustomMember;
import com.teamproject.culendar.service.FollowService;
import com.teamproject.culendar.service.InterestService;
import com.teamproject.culendar.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/members")
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final FollowService followService;
    private final InterestService interestService;

    // 회원 전체 조회 - 관리자용

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public String memberList(Model model){
        log.info("********** MemberController GET /members/list (memberList)");
        List<Member> memberList = memberRepository.findAll();
        model.addAttribute("list", memberList);
        return "admin/memberList";
    }

    // 회원 상세페이지
//    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping("/{id}")
    public String myProfile(@PathVariable("id") Long id, @AuthenticationPrincipal CustomMember customMember, Model model){
        log.info("********** MemberController GET /members/:id (myProfile) - id : {}", id);
        MemberDTO member = customMember.getMember();
        Follow follow = followService.findById(id);
        log.info("********** MemberController GET /members/:id (myProfile) - followId : {}", follow.getId());
        FollowDTO followDTO = new FollowDTO(follow);
        // TODO: 팔로우, 팔로워 수
        long followerCount = followDTO.getFollowerCount();
        long followingCount = followDTO.getFollowingCount();
        model.addAttribute("follow", followDTO);
        model.addAttribute("member", member);
        return "profile/memberProfile";
    }

/*

    // 다른 회원의 프로필 조회
    @GetMapping("/{id}/profile")
    public String profile(@PathVariable("id") Long id, Model model){
        log.info("********** MemberController GET /members/:id/profile - id : {}", id);
        MemberDTO member = memberService.findById(id);
        model.addAttribute("member", member);
        return "profile/memberProfile";
    }
*/

    // 팔로우, 팔로워 조회
    @GetMapping("/{id}/follow")
    public String followings(@PathVariable("id") Long id, Model model){
        log.info("********** MemberController GET /members/:userid/follow - userid : {}", id);
        List<Member> memberList = memberRepository.findAll();
        List<Follow> followList = followService.findByMemberId(id);// 팔로우한 사람들의 정보
        List<MemberDTO> followings = new ArrayList<>();
        for (Follow follow : followList) {
            MemberDTO member = new MemberDTO(follow.getFollow()); // 팔로우한 사람들의 정보
            followings.add(member); // 팔로우한 사람들의 정보를 리스트에 추가
            log.info("********** MemberController GET /members/:userid/follow - followings : {}", followings);
            log.info("********** MemberController GET /members/:userid/follow - member : {}", member);
            model.addAttribute("followings", followings);// 팔로우한 사람들의 정보를 모델에 추가
        }
        model.addAttribute("memberList", memberList);
        return "profile/follow";
    }

    @GetMapping("/{id}/follower")
    public String followers(@PathVariable("id") Long id, Model model){
        log.info("********** MemberController GET /members/:userid/follow - userid : {}", id);
        List<Follow> followerList = followService.findByFollowId(id);
        List<MemberDTO> followers = new ArrayList<>();
        for (Follow follow : followerList) {
            MemberDTO member = new MemberDTO(follow.getMember());
            followers.add(member);
            log.info("********** MemberController GET /members/:userid/follow - followers : {}", followers);
            log.info("********** MemberController GET /members/:userid/follow - member : {}", member);
            model.addAttribute("followers", followers);
        }
        return "profile/follower";
    }

    // 팔로우 처리
    @PostMapping("/{followid}/follow")
    public String follow (@PathVariable Long followid, @AuthenticationPrincipal CustomMember customMember){
        Long id = customMember.getMember().getId();
        log.info("********** MemberController POST /members/:id/follow - id : {}", id);
        log.info("********** MemberController POST /members/:id/follow - followId : {}", followid);
        FollowDTO followDTO = followService.follow(id, followid);
        log.info("********** MemberController POST /members/:id/follow - followDTO : {}", followDTO);
        return "redirect:/members/" + id + "/follow";
    }
    // 언팔로우 처리
    @PostMapping("/{followid}/unfollow")
    public String unfollow(@PathVariable Long followid, @AuthenticationPrincipal CustomMember customMember){
        Long id = customMember.getMember().getId();
        log.info("********** MemberController DELETE /members/:id/follow - id : {}", id);
        log.info("********** MemberController DELETE /members/:id/follow - followId : {}", followid);
        FollowDTO followDTO = followService.unfollow(id, followid);

        return "redirect:/members/" + id + "/follow";
    }

    // 회원 정보 수정
//    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, InterestForm interestForm, Model model){
        log.info("********** MemberController GET /members/:id/edit - id : {}", id);
        Member member = memberService.findById(id);
        model.addAttribute("member", member);
        model.addAttribute("interestForm", interestForm);
        return "profile/edit";
    }

    // 회원 정보 수정 처리
//    @PreAuthorize("hasRole('MEMBER')")
    @PostMapping("/{id}/edit")
    public String editPro(@PathVariable("id") Long id, MemberForm memberForm, InterestForm interestForm){
        log.info("********** MemberController POST /members/:id/edit - id : {}", id);
        log.info("********** MemberController POST /members/:id/edit - memberForm : {}", memberForm);
        // 회원 정보
        Long updateId = memberService.updateMember(memberForm);
        // 수정된 회원 정보 조회
        Member member = memberService.findById(updateId);
        // 기존 관심분야 삭제
        List<Interest> interestList = interestService.findByMemberId(updateId);
        for (Interest interest : interestList) {
            interestService.deleteInterest(interest.getId());
        }
        // 회원의 관심분야 수정
        for (ProgramType programType : interestForm.getInterestList()) {
            InterestForm interest = new InterestForm(programType);
            interest.setMember(member);
            interestService.saveInterest(interest);
        }
        return "redirect:/members/{id}";
    }

    // 회원 설정
//    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping("/{id}/setting")
    public String setting(@PathVariable("id") Long id, Model model){
        log.info("********** MemberController GET /members/:id/setting - id : {}", id);
        Member member = memberService.findById(id);
        model.addAttribute("member", member);
        return "member/setting";
    }

//    @PreAuthorize("hasRole('MEMBER')")
    @PostMapping("/{id}/setting")
    public String settingPro(@PathVariable("id") Long id){
        log.info("********** MemberController POST /members/:id/setting - id : {}", id);
        return "redirect:/members/{id}";
    }

    // 회원 탈퇴 - 비활성화
//    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping("/{id}/deactivate")
    public String deactivate(@PathVariable("id") Long id, Model model){
        log.info("********** MemberController GET /members/:id/deactivate - id : {}", id);
        Member member = memberService.findById(id);
        model.addAttribute("member", member);
        return "member/withdrawal";
    }
//    @PreAuthorize("hasRole('MEMBER')")
    @PostMapping("/{id}/deactivate")
    public String deactivatePro(@PathVariable("id") Long id){
        log.info("********** MemberController POST /members/:id/deactivate - id : {}", id);
        memberService.deactivateMember(id);
        return "redirect:/logout";
    }








    // 관리자 접근 허용
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String admin() {
        return "member/admin";
    }

    // 회원과 관리자 접근 허용
    @PreAuthorize("hasAnyRole('MEMBER', 'ADMIN')")
    @GetMapping("/member")
    public String member() {
        return "member/member";
    }

}

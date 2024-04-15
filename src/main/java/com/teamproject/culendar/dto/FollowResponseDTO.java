package com.teamproject.culendar.dto;

import com.teamproject.culendar.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FollowResponseDTO {
//    private Long followingCount;
    private List<Member> memberList;
    private List<MemberDTO> followingList;
}

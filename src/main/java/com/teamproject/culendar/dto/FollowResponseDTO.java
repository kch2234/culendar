package com.teamproject.culendar.dto;

import com.teamproject.culendar.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FollowResponseDTO {
    private List<MemberDTO> followingList;
    private List<MemberDTO> followerList;
    private List<Member> memberList;
}

package com.teamproject.culendar.dto;

import com.teamproject.culendar.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FollowFollowing {
//    private List<MemberDTO> followingList;
    private List<Member> memberList;
}

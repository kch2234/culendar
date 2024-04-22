package com.teamproject.culendar.dto;

import com.teamproject.culendar.domain.member.Follow;
import com.teamproject.culendar.domain.member.Member;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FollowDTO {

    private Long id;
    private MemberDTO followDTO;
    private MemberDTO memberDTO;

    public FollowDTO(Follow follow) {
        this.id = follow.getId();
        this.followDTO = new MemberDTO(follow.getFollow());
        this.memberDTO = new MemberDTO(follow.getMember());
    }
}

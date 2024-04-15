package com.teamproject.culendar.dto;

import com.teamproject.culendar.domain.member.Follow;
import com.teamproject.culendar.domain.member.Member;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FollowForm {
    private Long id;
    private LocalDateTime createDate;
    private Member member;
    private Member follow;

    public Follow toEntity() {
        Follow flw = new Follow();
        flw.setFollow(follow);
        flw.setMember(member);
        return flw;
    }
}

package com.teamproject.culendar.dto;

import com.teamproject.culendar.domain.member.Member;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FollowDTO {
    private Long id;
    private LocalDateTime createDate;
    private Member member;
    private Member follow;
    private boolean followState;
    private Long followCount;

    /*public FollowDTO(Member follow) {
        this.follow = follow;
    }*/
}

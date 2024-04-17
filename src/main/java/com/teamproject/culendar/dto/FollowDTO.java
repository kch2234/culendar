package com.teamproject.culendar.dto;

import com.teamproject.culendar.domain.member.Follow;
import com.teamproject.culendar.domain.member.Member;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FollowDTO {
    private long followerCount;
    private long followingCount;
    private Long id;
    private LocalDateTime createDate;
    private Member member;
    private Member follow;

    public FollowDTO(Follow follow){
        this.id = follow.getId();
        this.createDate = follow.getCreateDate();
        this.member = follow.getMember();
        this.follow = follow.getFollow();
    }
}

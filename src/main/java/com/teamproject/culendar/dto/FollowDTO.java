package com.teamproject.culendar.dto;

import com.teamproject.culendar.domain.member.Follow;
import com.teamproject.culendar.domain.member.Member;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FollowDTO {
    private Long id;
    private LocalDateTime createDate;
    private Member member;
    private Member follow;

    public FollowDTO(Follow entity) {
        id = entity.getId();
        createDate = entity.getCreateDate();
        member = entity.getMember();
        follow = entity.getFollow();
    }

    public FollowDTO(Member follow) {
        this.follow = follow;
    }
}

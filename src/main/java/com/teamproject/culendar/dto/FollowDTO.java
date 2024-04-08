package com.teamproject.culendar.dto;

import com.teamproject.culendar.domain.member.Follow;
import com.teamproject.culendar.domain.member.Member;
import lombok.Data;

import java.time.LocalDateTime;

// 팔로우 DTO // 팔로우 하는 사람, 팔로우 대상 // 팔로우 테이블
@Data
public class FollowDTO {
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

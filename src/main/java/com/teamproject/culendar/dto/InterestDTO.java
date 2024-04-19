package com.teamproject.culendar.dto;

import com.teamproject.culendar.domain.enumFiles.ProgramType;
import com.teamproject.culendar.domain.member.Interest;
import com.teamproject.culendar.domain.member.Member;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class InterestDTO {
    private Long id;
    private LocalDateTime createDate;
    private Member member;
    private ProgramType interest;

    public InterestDTO(Interest interest, MemberDTO memberDTO){
        this.id = interest.getId();
        this.createDate = interest.getCreateDate();
        this.member = memberDTO.toEntity();
        this.interest = interest.getInterest();
    }

    // DTO -> Entity
    public Interest toEntity() {
        Interest entity = new Interest();
        entity.setMember(member);
        entity.setInterest(interest);
        return entity;
    }
}

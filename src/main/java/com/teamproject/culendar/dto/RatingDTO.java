package com.teamproject.culendar.dto;

import com.teamproject.culendar.domain.member.Member;
import com.teamproject.culendar.domain.program.Program;
import com.teamproject.culendar.domain.program.Rating;
import lombok.Data;

@Data
public class RatingDTO {

    private Long id;
    private Long programId;
    private Program program;
    private ProgramDTO programDTO;
    private Long memberId;
    private Member member;
    private MemberDTO memberDTO;
    private Long rating;

    public RatingDTO(Rating rating) {
        this.id = rating.getId();
        this.programId = rating.getProgram().getId();
        this.programDTO = new ProgramDTO(rating.getProgram());
        this.memberId = rating.getMember().getId();
        this.memberDTO = new MemberDTO(rating.getMember());
        this.rating = rating.getRating();
    }

    public RatingDTO(Long programId, Long memberId, Long rating) {
        this.programId = programId;
        this.memberId = memberId;
        this.rating = rating;
    }

    public Rating toEntity() {
        Rating rating = new Rating();
        rating.setProgram(this.program);
        rating.setMember(this.member);
        rating.setRating(this.rating);
        return rating;

    }
}

package com.teamproject.culendar.dto;

import com.teamproject.culendar.domain.enumFiles.ProgramType;
import com.teamproject.culendar.domain.member.Interest;
import com.teamproject.culendar.domain.member.Member;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class InterestForm {
    private Long id;
    private ProgramType interest;
    private List<ProgramType> interestList = new ArrayList<>();
    private Member member;

    public Interest toEntity() {
        Interest interests = new Interest();
        interests.setMember(member);
        interestList.add(interest);
        return interests;
    }
}

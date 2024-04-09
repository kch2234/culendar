package com.teamproject.culendar.dto;

import com.teamproject.culendar.domain.enumFiles.ProgramType;
import com.teamproject.culendar.domain.member.Interest;
import com.teamproject.culendar.domain.member.Member;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
public class InterestForm {
    private Long id;
    private ProgramType interest;
    private List<ProgramType> interestList = new ArrayList<>();
    private Member member;

    public Interest toEntity() {
        Interest interests = new Interest();
        interests.setMember(member);
        setInterestList(interest);
        return interests;
    }

    public void setInterestList(ProgramType interest) {
        this.interest = interest;
        interestList.add(interest);
    }
}

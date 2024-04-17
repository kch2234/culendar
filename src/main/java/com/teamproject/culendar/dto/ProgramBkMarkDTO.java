package com.teamproject.culendar.dto;

import com.teamproject.culendar.domain.enumFiles.Location;
import com.teamproject.culendar.domain.member.Member;
import com.teamproject.culendar.domain.program.Program;
import com.teamproject.culendar.domain.program.ProgramBkmark;
import lombok.Data;

@Data
public class ProgramBkMarkDTO {

    private Long id;
    private Long memberId;
    private Member member;
    private Long programId;
    private Program program;
    private Location location;

    public ProgramBkmark toEntity() {
        ProgramBkmark programBkmark = new ProgramBkmark();
        programBkmark.setMember(member);
        programBkmark.setProgram(program);
        programBkmark.setLocation(location);

        return programBkmark;
    }
}

package com.teamproject.culendar.dto;

import com.teamproject.culendar.domain.enumFiles.Location;
import com.teamproject.culendar.domain.enumFiles.ProgramType;
import com.teamproject.culendar.domain.program.Program;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProgramDTO {

    private String title;
    private Long seq;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String programTime;
    private ProgramType programType;
    private String thumbnail;
    private Location location;
    private Double locationX;
    private Double locationY;

    public Program toEntity() {
        Program program = new Program();
        program.setTitle(this.title);
        program.setSeq(this.seq);
        program.setStartDate(this.startDate);
        program.setEndDate(this.endDate);
        program.setProgramTime(this.programTime);
        program.setProgramType(this.programType);
        program.setThumbnail(this.thumbnail);
        program.setLocation(this.location);
        program.setLocationX(this.locationX);
        program.setLocationY(this.locationY);
        return program;
    }

}

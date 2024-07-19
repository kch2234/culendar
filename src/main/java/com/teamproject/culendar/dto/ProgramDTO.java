package com.teamproject.culendar.dto;

import com.teamproject.culendar.domain.enumFiles.Location;
import com.teamproject.culendar.domain.enumFiles.ProgramType;
import com.teamproject.culendar.domain.program.Program;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ProgramDTO {

    private Long id;
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
        program.setId(this.id);
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

    public ProgramDTO(Program program) {
        this.id = program.getId();
        this.title = program.getTitle();
        this.seq = program.getSeq();
        this.startDate = program.getStartDate();
        this.endDate = program.getEndDate();
        this.programTime = program.getProgramTime();
        this.programType = program.getProgramType();
        this.thumbnail = program.getThumbnail();
        this.location = program.getLocation();
        this.locationX = program.getLocationX();
        this.locationY = program.getLocationY();
    }

    public ProgramDTO(Program program, Timestamp startDate, Timestamp endDate) {
        this.id = program.getId();
        this.title = program.getTitle();
        this.seq = program.getSeq();
        this.startDate = startDate.toLocalDateTime();
        this.endDate = endDate.toLocalDateTime();
        this.programTime = program.getProgramTime();
        this.programType = program.getProgramType();
        this.thumbnail = program.getThumbnail();
        this.location = program.getLocation();
        this.locationX = program.getLocationX();
        this.locationY = program.getLocationY();
    }

}

package com.teamproject.culendar.dto;

import com.teamproject.culendar.domain.enumFiles.ProgramType;
import com.teamproject.culendar.domain.program.Program;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Optional;

@Data
public class CalendarDTO {

    private String title;
    private LocalDateTime  start;
    private LocalDateTime end;
    private String color;
    private String id;

    public CalendarDTO(Program program) {
        this.title = program.getTitle();
        this.start = program.getStartDate();
        this.end = program.getEndDate();
        this.id = program.getSeq().toString();
        if (program.getProgramType() == ProgramType.CONCERT){
            this.color = "#FFD700";
        } else if (program.getProgramType() == ProgramType.DRAMA){
            this.color = "#FF6347";
        } else if (program.getProgramType() == ProgramType.EXHIBITION){
            this.color = "#20B2AA";
        } else if (program.getProgramType() == ProgramType.MUSICAL){
            this.color = "#9370DB";
        } else {
            this.color = "#FFA07A";
        }
    }

}

package com.teamproject.culendar.dto;

import com.teamproject.culendar.domain.program.Program;
import lombok.Data;

@Data
public class SearchDTO {
    private String title;
    private String location;
    private Double locationX;
    private Double locationY;
    private String id;

    public SearchDTO(Program program) {
        this.title = program.getTitle();
        this.location = program.getLocation().toString();
        this.locationX = program.getLocationX();
        this.locationY = program.getLocationY();
        this.id = program.getSeq().toString();
    }
}

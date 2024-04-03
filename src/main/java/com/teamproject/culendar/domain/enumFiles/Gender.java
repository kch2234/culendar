package com.teamproject.culendar.domain.enumFiles;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender {
    // *****    성별    *****

    MALE("남"),FEMALE("여"), ALL("모두");
    private final String value;
}

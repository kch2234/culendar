package com.teamproject.culendar.domain.enumfiles;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender {
    // *****    성별    *****

    MALE("남"),FEMALE("여");
    private final String value;
}

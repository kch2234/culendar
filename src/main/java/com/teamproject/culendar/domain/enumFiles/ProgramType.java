package com.teamproject.culendar.domain.enumFiles;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProgramType {
    // *****    작품 종류   *****

    MOVIE("영화"), MUSICAL("뮤지컬"), CONCERT("콘서트"), EXHIBITION("전시회"), ETC("기타");
    private final String value;
}

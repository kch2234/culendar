package com.teamproject.culendar.domain.enumFiles;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProgramType {
    // *****    작품 종류   *****

    DRAMA("연극"), CONCERT("음악/콘서트"), EXHIBITION("전시"), MUSICAL("뮤지컬/오페라"), ETC("기타");
    private final String value;
}

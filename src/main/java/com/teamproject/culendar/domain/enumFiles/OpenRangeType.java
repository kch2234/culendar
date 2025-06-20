package com.teamproject.culendar.domain.enumFiles;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OpenRangeType {
    // *****    공개 범위    *****

    ALL("전체공개"), MEMBER("회원"), FRIEND("친구공개"), PRIVATE("비공개");
    private final String value;
}

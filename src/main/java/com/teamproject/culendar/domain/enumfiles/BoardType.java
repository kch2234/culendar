package com.teamproject.culendar.domain.enumfiles;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BoardType {
    // *****    게시판 분류    *****

    INFO("정보"),REVIEW("리뷰");
    private final String value;
}

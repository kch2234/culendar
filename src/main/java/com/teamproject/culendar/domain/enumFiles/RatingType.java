package com.teamproject.culendar.domain.enumFiles;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RatingType {
    // *****    문장형 평가 내용   *****
        //TODO 문장형 평가 내용 수정
        VERYGOOD("매우 좋아요"),
        GOOD("좋아요"),
        NORMAL("보통이에요"),
        BAD("싫어요"),
        VERYBAD("매우 싫어요");
        private final String value;
}

package com.teamproject.culendar.domain.enumfiles;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReasonList {
    // *****    탈퇴 사유    *****
    //TODO: 탈퇴 사유 리스트 추가
    A("A"), B("B"), C("C");
    private final String value;
}

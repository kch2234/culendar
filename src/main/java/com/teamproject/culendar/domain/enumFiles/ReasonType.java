package com.teamproject.culendar.domain.enumFiles;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReasonType {
    // *****    탈퇴 사유    *****
    //TODO: 탈퇴 사유 리스트 추가
    A("A"), B("B"), C("C");
    private final String value;
}

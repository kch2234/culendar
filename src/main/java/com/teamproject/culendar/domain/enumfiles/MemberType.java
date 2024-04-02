package com.teamproject.culendar.domain.enumfiles;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberType {
    // *****    회원 성향    *****
    //TODO: 회원 성향 추가
    A("A"), B("B"), C("C"), D("D"), E("E"), F("F");
    private final String value;
}

package com.teamproject.culendar.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberType {
    A("A"), B("B"), C("C"), D("D"), E("E"), F("F");
    private final String value;
}

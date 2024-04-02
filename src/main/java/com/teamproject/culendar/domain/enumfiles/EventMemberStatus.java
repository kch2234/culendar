package com.teamproject.culendar.domain.enumfiles;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventMemberStatus {

    WAIT("대기"), ACCEPT("수락"), REJECT("거절");
    private final String value;
}

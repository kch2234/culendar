package com.teamproject.culendar.domain.enumfiles;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventMemberStatus {
    // *****    모임 일정 신청자 상태    *****

    WAIT("대기"), ACCEPT("수락"), REJECT("거절");
    private final String value;
}

package com.teamproject.culendar.domain.enumFiles;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventMemberStatus {
    // *****    모임 일정 신청자 상태    *****

    WAIT("대기"), ACCEPT("확정"), REJECT("거절");
    private final String value;
}

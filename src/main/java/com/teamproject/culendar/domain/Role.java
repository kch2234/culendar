package com.teamproject.culendar.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    MEMBER("ROLE_MEMBER"), ADMIN("ROLE_ADMIN"), CANCEL("ROLE_CANCEL");
    private final String value;
}

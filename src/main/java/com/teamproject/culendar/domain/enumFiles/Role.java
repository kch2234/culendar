package com.teamproject.culendar.domain.enumFiles;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
  // *****    사용자 권한    *****
  ADMIN("관리자"), MEMBER("회원"), CANCEL("탈퇴회원");
  private final String value;
}
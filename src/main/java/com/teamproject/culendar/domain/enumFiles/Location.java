package com.teamproject.culendar.domain.enumFiles;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Location {
    // *****    지역 종류   *****

    SEOUL("서울"), BUSAN("부산"), DAEGU("대구"), INCHEON("인천"), GWANGJU("광주"), DAEJEON("대전"), ULSAN("울산"), SEJONG("세종"), GYEONGGI("경기"), GANGWON("강원"), CHUNGBUK("충북"), CHUNGNAM("충남"), JEONBUK("전북"), JEONNAM("전남"), GYEONGBUK("경북"), GYEONGNAM("경남"), JEJU("제주"), ETC("기타"), ALL("전체");
    private final String value;
}

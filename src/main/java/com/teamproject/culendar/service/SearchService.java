package com.teamproject.culendar.service;

import com.teamproject.culendar.domain.enumFiles.Location;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SearchService {

    public Location mapStringToLocation(String locationString) {
        log.info("***** SearchService - mapStringToLocation ***** :{}", locationString);
        String normalizedLocation = normalizeLocationString(locationString);
        log.info("***** SearchService - mapStringToLocation - normalizedLocation :{}", normalizedLocation);
        try {
            log.info("***** SearchService - mapStringToLocation - Location.valueOf(normalizedLocation.toUpperCase()) :{}", Location.valueOf(normalizedLocation.toUpperCase()));
            return Location.valueOf(normalizedLocation.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.error("***** SearchService - mapStringToLocation - IllegalArgumentException :{}", e);
            return Location.ETC;  //매핑할 수 없는 경우 기타로 처리
        }
    }

    private String normalizeLocationString(String locationString) {
        log.info("***** SearchService - normalizeLocationString ***** :{}", locationString);
        switch(locationString) {
            case "서울": return "SEOUL";
            case "부산": return "BUSAN";
            case "대구": return "DAEGU";
            case "인천": return "INCHEON";
            case "광주": return "GWANGJU";
            case "대전": return "DAEJEON";
            case "울산": return "ULSAN";
            case "세종": return "SEJONG";
            case "경기": return "GYEONGGI";
            case "강원": return "GANGWON";
            case "충북": return "CHUNGBUK";
            case "충남": return "CHUNGNAM";
            case "전북": return "JEONBUK";
            case "전남": return "JEONNAM";
            case "경북": return "GYEONGBUK";
            case "경남": return "GYEONGNAM";
            case "제주": return "JEJU";
            case "기타": return "ETC";
            case "전체": return "ALL";
            default: return locationString;
        }
    }
}

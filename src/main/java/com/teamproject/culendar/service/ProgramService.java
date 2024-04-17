package com.teamproject.culendar.service;

import com.teamproject.culendar.domain.calendar.CalendarContent;
import com.teamproject.culendar.domain.enumFiles.Location;
import com.teamproject.culendar.domain.enumFiles.ProgramType;
import com.teamproject.culendar.domain.program.Program;
import com.teamproject.culendar.dto.ProgramDTO;
import com.teamproject.culendar.repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ProgramService {

    private final ProgramRepository programRepository;

    // 작품 하나 DB에 저장
    public Long save(ProgramDTO programDTO) {

        Program saveProgram = programRepository.save(programDTO.toEntity());
        return saveProgram.getSeq();
    }

    // 작품 하나 크롤링
    public ProgramDTO getProgram(Long num) {

        String mainURL = "https://www.culture.go.kr/oneeye/oneEyeView.do?seq=";
        Long seq = num;

        String targetURL = mainURL + seq;
        Document doucment = null;
        try {
            doucment = Jsoup.connect(targetURL).get();
        } catch (IOException e) {
            return null;
        }


        log.info(String.valueOf(seq));

        // 제목 크롤링
        Element title = doucment.select("#content > div > section.section-view > div > div > div.info-view > div.wrap-info > p").first();
        if (title == null || title.text().equals("")) {
            return null;
        }
        String titleText = title.text();
        log.info(titleText);

        // 분류 크롤링
        Element programType = doucment.select("#content > div > section.section-view > div > div > div.info-view > div.wrap-info > ul > li.tag.reverse > span").first();
        if (programType == null || programType.text().equals("")) {
            return null;
        }
        String programTypeText = programType.text();
        ProgramType compareProgramType = compareProgramType(programTypeText);
        if (compareProgramType == null) {
            return null;
        }
        log.info(programTypeText);

        // 기간 크롤링
        Element period = doucment.select("#content > div > section.section-view > div > div > div.info-view > div.wrap-info > div > dl:nth-child(1) > dd").first();
        if (period == null || period.text().equals("")) {
            return null;
        }
        String periodText = period.text();
        log.info(periodText);

        // String to LocalDateTime
        DateTimeFormatter formatterDash = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter formatterDot = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        String[] dateInString = new String[2];
        String[] lastDate = new String[2];
        try {
            if (periodText.contains(" ~ ")) {
                dateInString = periodText.split(" ~ ");
            } else if (periodText.contains("~")) {
                dateInString = periodText.split("~");
            }
            lastDate = dateInString[1].split(" ");
        } catch (Exception e) {
            return null;
        }
        log.info(lastDate[0]);
        dateInString[0] += " 00:00:00";
        lastDate[0] += " 23:59:59";
        LocalDateTime startDate;
        try {
            if (dateInString[0].contains(".")) {
                startDate = LocalDateTime.parse(dateInString[0], formatterDot);
            } else {
                startDate = LocalDateTime.parse(dateInString[0], formatterDash);
            }
        } catch (Exception e) {
            return null;
        }
        LocalDateTime endDate;
        try {
            if (lastDate[0].contains(".")) {
                endDate = LocalDateTime.parse(lastDate[0], formatterDot);
            } else {
                endDate = LocalDateTime.parse(lastDate[0], formatterDash);
            }
        } catch (Exception e) {
            return null;
        }

        if (startDate == null || endDate == null) {
            return null;
        }
        log.info(startDate.toString());
        log.info(endDate.toString());

        // 시간 크롤링
        String timeText = "";
        Element time = doucment.select("#content > div > section.section-view > div > div > div.info-view > div.wrap-info > div > dl:nth-child(2) > dd").first();
        if (time == null || time.text().equals("")) {
            timeText = "정보 없음";
        } else {
            timeText = time.text();
            log.info(timeText);
        }

        // 지역 크롤링
        Element location = doucment.select("#content > div > section.section-view > div > div > div.info-view > div.wrap-info > div > dl:nth-child(4) > dd").first();
        if (location == null || location.text().equals("")) {
            return null;
        }

//        Element location = doucment.select("#areaTabInfo_01 > div.txt-wrap > div:nth-child(1) > dl:nth-child(2) > dd").first();
        String locationText = location.text();
//        locationText.substring(0,2);

        Location compareLocation = compareLocation(locationText);
        if (compareLocation == null) {
            return null;
        }

        log.info(locationText);

        // x 좌표 크롤링
        Element locationX = doucment.select("#cul_gps_x").first();
        if (locationX == null || locationX.val().equals("")) {
            return null;
        }
        String locationXText = locationX.val();
        log.info(locationXText);

        // y 좌표 크롤링
        Element locationY = doucment.select("#cul_gps_y").first();
        if (locationY == null || locationY.val().equals("")) {
            return null;
        }
        String locationYText = locationY.val();
        log.info(locationYText);

        // 이미지 크롤링
        String imageUrl = "";
        Element image = doucment.select("#content > div > section.section-view > div > div > div.img-view > img").first();
        if (image == null || image.attr("src").equals("")) {
            log.info("이미지 없음");
        } else {
            imageUrl = "http://www.culture.go.kr/" + image.attr("src");
            log.info(imageUrl);
        }


        ProgramDTO programDTO = new ProgramDTO();

        programDTO.setTitle(titleText);
        programDTO.setSeq(seq);
        programDTO.setProgramType(compareProgramType);
        programDTO.setStartDate(startDate);
        programDTO.setEndDate(endDate);
        programDTO.setProgramTime(timeText);
        programDTO.setLocation(compareLocation);
        programDTO.setLocationX(Double.parseDouble(locationXText));
        programDTO.setLocationY(Double.parseDouble(locationYText));
        programDTO.setThumbnail(imageUrl);

        return programDTO;
    }

    // 크롤링한 지역 String값 ENUM으로 변환
    public Location compareLocation(String locationText) {

        for (Location location : Location.values()) {
            if (locationText.substring(0,2).contains(location.getValue())) {
                return location;
            }
        }
        return null;
    }

    // 크롤링한 분류 String값 ENUM으로 변환
    public ProgramType compareProgramType(String programTypeText) {

        for (ProgramType programType : ProgramType.values()) {
            if (programTypeText.contains(programType.getValue())) {
                return programType;
            }
        }
        return null;
    }


    public Long FindBySeq(Long seq) {
        Program findProgram = programRepository.findBySeq(seq).orElse(null);
        if (findProgram != null) {
            return findProgram.getSeq();
        }
        return null;
    }

    public void saveProgram(ProgramDTO programDTO) {
        if (FindBySeq(programDTO.getSeq()) != null) {
            log.info("******** ProgramController POST /program/add - 중복된 seq 값입니다.");
            return;
        }
        ProgramDTO program = getProgram(programDTO.getSeq());
        if (program == null) {
//            log.info("******** ProgramController POST /program/add - 서버에 존재하지 않는 seq 값입니다. : " + programDTO.getSeq());
            return;
        }
        save(program);
    }

    public ProgramDTO getOneProgram(Long id) {
        Optional<Program> program = programRepository.findById(id);
        if (program.isPresent()) {
            return new ProgramDTO(program.get());
        }
        return null;
    }

    public List<ProgramDTO> searchProgram(String keyword) {

        log.info("******** ProgramService - searchProgram - keyword: {}", keyword);

        // keyword를 포함하는 작품 리스트 최대 30개 가져오기
        List<Program> programList = programRepository.findByTitleContaining(keyword);
        // 리스트 최대 길이 30으로 자르기
        if (programList.size() > 20) {
            programList = programList.subList(0, 20);
        }

        // List<Program> to List<ProgramDTO>
        List<ProgramDTO> programDTOList = programList.stream()
                .map(ProgramDTO::new)
                .collect(Collectors.toList());

        return programDTOList;
    }



}

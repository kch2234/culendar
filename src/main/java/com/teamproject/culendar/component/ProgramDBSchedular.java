package com.teamproject.culendar.component;

import com.teamproject.culendar.dto.ProgramDTO;
import com.teamproject.culendar.service.ProgramService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProgramDBSchedular {

    private final ProgramService programService;

//    static Long seq = 17000L;
//    static Long seq = 200460L;
//    static Long seq = 230000L; // 22년 11월 부터 시작
//    static Long seq = 250000L; //23년 7월 부터 시작
    static Long seq = 270000L; //24년 3월 부터 시작

    // TODO 어느정도 진행되면 종료 되게

    // 매 1분마다 실행
//    @Scheduled(cron = "0 0/1 * * * *")
    @Scheduled(fixedDelay = 6000) // 작업 종료 후 6초 후에 다시 실행
    public void scheduleProgram() {

        /*int i = 0;

        log.info("******** ProgramDBSchedular scheduleProgram - 크롤링 시작 현재 seq : " + seq + " ********");

        // seq값 1000개까지 크롤링
        do {
            ProgramDTO program = new ProgramDTO();
            program.setSeq((long) seq);
            programService.saveProgram(program);
            seq++;
            i++;

        } while (i != 1000 && seq < 273000L);

    log.info("******** ProgramDBSchedular scheduleProgram - 크롤링 완료 현재 seq : " + seq + " ********");*/

    }


}

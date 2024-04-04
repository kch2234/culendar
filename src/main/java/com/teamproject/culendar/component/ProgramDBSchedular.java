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

    static Long seq = 272300L;
    // TODO 어느정도 진행되면 종료 되게

    // 매 1분마다 실행
    @Scheduled(cron = "0 0/1 * * * *")
    public void scheduleProgram() {

        int i = 0;

        // seq값 1부터 100까지 크롤링, 다음 크롤링은 100부터 200까지
        do {
            ProgramDTO program = new ProgramDTO();
            program.setSeq((long) seq);
            programService.saveProgram(program);
            seq++;
            i++;

        } while (i != 500);

    log.info("******** ProgramDBSchedular scheduleProgram - 크롤링 완료");
    }


}

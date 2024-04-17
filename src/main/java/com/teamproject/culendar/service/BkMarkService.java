package com.teamproject.culendar.service;

import com.teamproject.culendar.domain.program.ProgramBkmark;
import com.teamproject.culendar.dto.ProgramBkMarkDTO;
import com.teamproject.culendar.repository.MemberRepository;
import com.teamproject.culendar.repository.ProgramBkMarkRepository;
import com.teamproject.culendar.repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BkMarkService {

    private final ProgramBkMarkRepository programBkMarkRepository;
    private final MemberRepository memberRepository;
    private final ProgramRepository programRepository;

    public boolean addProgramBkmark(ProgramBkMarkDTO programBkMarkDTO) {
        log.info("** BkMarkService - addProgramBkmark - programBkMarkDTO: {}", programBkMarkDTO);

        // 중복 체크
        ProgramBkmark findProgramBkMark = programBkMarkRepository.findByMemberIdAndProgramId(programBkMarkDTO.getMemberId(), programBkMarkDTO.getProgramId());
        if (findProgramBkMark != null) {
            log.info("** 이미 북마크에 추가된 프로그램입니다.");
            return false;
        }

        programBkMarkDTO.setMember(memberRepository.findById(programBkMarkDTO.getMemberId()).get());
        programBkMarkDTO.setProgram(programRepository.findById(programBkMarkDTO.getProgramId()).get());
        programBkMarkDTO.setLocation(programBkMarkDTO.getProgram().getLocation());

        ProgramBkmark programBkmark = programBkMarkDTO.toEntity();

        programBkMarkRepository.save(programBkmark);

        return true;
    }

    public void deleteProgramBkmark(ProgramBkMarkDTO programBkMarkDTO) {
        log.info("** BkMarkService - deleteProgramBkmark - programBkMarkDTO: {}", programBkMarkDTO);
        ProgramBkmark findProgramBkMark = programBkMarkRepository.findByMemberIdAndProgramId(programBkMarkDTO.getMemberId(), programBkMarkDTO.getProgramId());

        programBkMarkRepository.delete(findProgramBkMark);
    }
}

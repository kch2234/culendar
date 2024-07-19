package com.teamproject.culendar.service;

import com.teamproject.culendar.domain.board.Board;
import com.teamproject.culendar.domain.board.BoardBkmark;
import com.teamproject.culendar.domain.enumFiles.Location;
import com.teamproject.culendar.domain.enumFiles.ProgramType;
import com.teamproject.culendar.domain.program.Program;
import com.teamproject.culendar.domain.program.ProgramBkmark;
import com.teamproject.culendar.dto.BoardBkMarkDTO;
import com.teamproject.culendar.dto.BoardDTO;
import com.teamproject.culendar.dto.ProgramBkMarkDTO;
import com.teamproject.culendar.dto.ProgramDTO;
import com.teamproject.culendar.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BkMarkService {

    private final ProgramBkMarkRepository programBkMarkRepository;
    private final MemberRepository memberRepository;
    private final ProgramRepository programRepository;
    private final BoardRepository boardRepository;
    private final BoardBkMarkRepository boardBkMarkRepository;

    // 작품 북마크 추가
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

    // 게시글 북마크 추가
    public boolean addBoardBkmark(BoardBkMarkDTO boardBkMarkDTO) {
        log.info("** BkMarkService - addBoardBkmark - boardBkMarkDTO: {}", boardBkMarkDTO);
        // 중복 체크
        long boardId = boardBkMarkDTO.getBoardId();
        long memberId = boardBkMarkDTO.getMemberId();
        log.info("** memberId: {}, boardId: {}", memberId, boardId);
        BoardBkmark findBoardBkMark = boardBkMarkRepository.findBoardBkmarkByMemberIdAndBoardId(memberId, boardId);
        log.info("** findBoardBkMark: {}", findBoardBkMark);

        if (findBoardBkMark != null) {
            log.info("** 이미 북마크에 추가된 게시글입니다.");
            return false;
        }

        boardBkMarkDTO.setMember(memberRepository.findById(boardBkMarkDTO.getMemberId()).get());
        boardBkMarkDTO.setProgram(programRepository.findById(boardBkMarkDTO.getProgramId()).get());
        boardBkMarkDTO.setBoard(boardRepository.findById(boardBkMarkDTO.getBoardId()).get());

        BoardBkmark boardBkmark = boardBkMarkDTO.toEntity();

        boardBkMarkRepository.save(boardBkmark);

        return true;
    }

    // 작품 북마크 삭제
    public void deleteProgramBkmark(ProgramBkMarkDTO programBkMarkDTO) {
        log.info("** BkMarkService - deleteProgramBkmark - programBkMarkDTO: {}", programBkMarkDTO);
        ProgramBkmark findProgramBkMark = programBkMarkRepository.findByMemberIdAndProgramId(programBkMarkDTO.getMemberId(), programBkMarkDTO.getProgramId());

        programBkMarkRepository.delete(findProgramBkMark);
    }

    // 게시글 북마크 삭제
    public void deleteBoardBkmark(BoardBkMarkDTO boardBkMarkDTO) {
        log.info("** BkMarkService - deleteBoardBkmark - boardBkMarkDTO: {}", boardBkMarkDTO);
        BoardBkmark findBoardBkMark = boardBkMarkRepository.findBoardBkmarkByMemberIdAndBoardId(boardBkMarkDTO.getMemberId(), boardBkMarkDTO.getBoardId());

        boardBkMarkRepository.delete(findBoardBkMark);
    }

    // 작품 북마크 체크
    public boolean checkProgramBkmark(Long memberId, Long programId){
        log.info("** BkMarkService - checkProgramBkmark - memberId: {}, programId: {}", memberId, programId);
        ProgramBkmark findProgramBkMark = programBkMarkRepository.findByMemberIdAndProgramId(memberId, programId);
        if(findProgramBkMark != null){
            return true;
        }
        return false;
    }

    // 게시글 북마크 체크
    public boolean checkBoardBkmark(Long memberId, Long boardId){
        log.info("** BkMarkService - checkBoardBkmark - memberId: {}, boardId: {}", memberId, boardId);
        BoardBkmark findBoardBkMark = boardBkMarkRepository.findBoardBkmarkByMemberIdAndBoardId(memberId, boardId);
        if(findBoardBkMark != null){
            return true;
        }
        return false;
    }

    // 작품 북마크 최신순으로 페이저블 조회
    public Page<ProgramDTO> findProgramBkmarkByMemberIdWithPaging(Long memberId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Program> programPage = programBkMarkRepository.findProgramBkmarkByMemberIdWithPaging(memberId, pageable);

        // Program 엔티티를 ProgramDTO로 변환
        Page<ProgramDTO> programDTOPage = programPage.map(program -> new ProgramDTO(program));

        return programDTOPage;
    }



}

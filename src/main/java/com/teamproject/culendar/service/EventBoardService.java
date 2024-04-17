package com.teamproject.culendar.service;

import com.teamproject.culendar.domain.board.Board;
import com.teamproject.culendar.domain.board.EventBoard;
import com.teamproject.culendar.domain.enumFiles.ProgramType;
import com.teamproject.culendar.domain.program.Program;
import com.teamproject.culendar.dto.EventBoardDTO;
import com.teamproject.culendar.dto.EventBoardForm;
import com.teamproject.culendar.dto.PageRequestDTO;
import com.teamproject.culendar.repository.EventBoardRepository;
import com.teamproject.culendar.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class EventBoardService {

  private final EventBoardRepository eventBoardRepository;
  private final MemberRepository memberRepository;

  // 모임 등록
  public Long save(EventBoardForm eventBoardForm) {
    EventBoard entity = eventBoardForm.toEntity();
    EventBoard savedBoard = eventBoardRepository.save(entity);

    return savedBoard.getId();
  }

  // 모임 목록 불러오기
  public Page<EventBoard> getListWithPaging(PageRequestDTO pageRequestDTO) {
    Pageable pageable = PageRequest.of(
        pageRequestDTO.getPage() - 1,
        pageRequestDTO.getSize(),
        Sort.by("id").descending());

    Page<EventBoard> result = eventBoardRepository.findAll(pageable);

    return result;
  }

  // 모임 프로그램타입별 목록 불러오기
  public Page<EventBoard> getListWithProgramType(PageRequestDTO pageRequestDTO, ProgramType programType) {
    Pageable pageable = PageRequest.of(
        pageRequestDTO.getPage() - 1,
        pageRequestDTO.getSize(),
        Sort.by("id").descending());

    Page<EventBoard> result = eventBoardRepository.findByProgramType(programType, pageable);
    return result;
  }

  // 모임 인기순(북마크순) 목록 불러오기
  public Page<EventBoard> getListWithBkMark(PageRequestDTO pageRequestDTO) {
    Pageable pageable = PageRequest.of(
        pageRequestDTO.getPage() - 1,
        pageRequestDTO.getSize(),
        Sort.by("id").descending());

    Page<EventBoard> result = eventBoardRepository.findOrderByBkMark(pageable);
    return result;
  }

  // 모임 조회
  public EventBoardDTO getOneBoard(Long id) {
    EventBoard eventBoard = eventBoardRepository.findById(id).orElse(null);
    eventBoard.setViewCount(eventBoard.getViewCount() + 1);

    return new EventBoardDTO(eventBoard);
  }

  // 모임 삭제
  public void deleteOneBoard(Long id) { eventBoardRepository.deleteById(id); }

  // 모임 수정
  public void updateOneBoard(EventBoardForm eventBoardForm) {
    EventBoard findEventBoard = eventBoardRepository.findById(eventBoardForm.getId()).orElse(null);
    findEventBoard.setTitle(eventBoardForm.getTitle());
    findEventBoard.setContent(eventBoardForm.getContent());
  }


}

package com.teamproject.culendar.service;

import com.teamproject.culendar.domain.board.Board;
import com.teamproject.culendar.domain.board.EventBoard;
import com.teamproject.culendar.dto.BoardDTO;
import com.teamproject.culendar.dto.EventBoardDTO;
import com.teamproject.culendar.dto.EventBoardForm;
import com.teamproject.culendar.dto.PageRequestDTO;
import com.teamproject.culendar.repository.EventBoardRepository;
import com.teamproject.culendar.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
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

//  // 모임 목록 불러오기 (페이징 X)
//  public List<BoardDTO> getList() {
//    List<Board> all = eventBoardRepository.findAll();
//    List<BoardDTO> list = all.stream()
//        .map(b -> new BoardDTO(b))
//        .collect(Collectors.toList());
//
//    return list;
//  }

  // 모임 목록 불러오기
  public Page<EventBoard> getListWithPaging(PageRequestDTO pageRequestDTO) {
    Pageable pageable = PageRequest.of(
        pageRequestDTO.getPage() - 1,
        pageRequestDTO.getSize(),
        Sort.by("id").descending());

    Page<EventBoard> result = eventBoardRepository.findAll(pageable);

    return result;
  }

  // 모임 조회
  public EventBoardDTO getOneBoard(Long id) {
    EventBoard board = eventBoardRepository.findById(id).orElse(null);
//    board.setViewCount(board.getViewCount() + 1);  TODO 수정

    return new EventBoardDTO(board);
  }

  // 모임 삭제
  public void deleteOneBoard(Long id) {
    eventBoardRepository.deleteById(id);
  }

  // 모임 수정
  public void updateOneBoard(EventBoardForm eventBoardForm) {
    EventBoard findBoard = eventBoardRepository.findById(eventBoardForm.getId()).orElse(null);
    findBoard.setTitle(eventBoardForm.getTitle());
    findBoard.setContent(eventBoardForm.getContent());
  }



}

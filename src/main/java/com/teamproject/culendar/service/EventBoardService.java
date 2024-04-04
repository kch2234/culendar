package com.teamproject.culendar.service;

import com.teamproject.culendar.domain.board.Board;
import com.teamproject.culendar.domain.board.EventBoard;
import com.teamproject.culendar.dto.BoardDTO;
import com.teamproject.culendar.dto.BoardForm;
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

  // 게시글 등록
  public Long save(BoardForm boardForm) {
    Board entity = EventBoardForm.toEntity();
    Board savedBoard = eventBoardRepository.save(entity);

    return savedBoard.getId();
  }

//  // 게시글 목록 불러오기 (페이징 X)
//  public List<BoardDTO> getList() {
//    List<Board> all = eventBoardRepository.findAll();
//    List<BoardDTO> list = all.stream()
//        .map(b -> new BoardDTO(b))
//        .collect(Collectors.toList());
//
//    return list;
//  }

  // 게시글 목록 불러오기
  public Page<EventBoard> getListWithPaging(PageRequestDTO pageRequestDTO) {
    Pageable pageable = PageRequest.of(
        pageRequestDTO.getPage() - 1,
        pageRequestDTO.getSize(),
        Sort.by("id").descending());

    Page<EventBoard> result = eventeventBoardRepository.findAll(pageable);

    return result;
  }

  // 게시글 조회
  public BoardDTO getOneBoard(Long id) {
    EventBoard board = eventBoardRepository.findById(id).orElse(null);
    board.setViewCount(board.getViewCount() + 1);

    return new BoardDTO(board);
  }

  // 게시글 삭제
  public void deleteOneBoard(Long id) {
    eventBoardRepository.deleteById(id);
  }

  public void updateOneBoard(BoardForm boardForm) {
    Board findBoard = memberRepository.findById(boardForm.getId()).orElse(null);
    findBoard.setTitle(boardForm.getTitle());
    findBoard.setContent(boardForm.getContent());
  }



}

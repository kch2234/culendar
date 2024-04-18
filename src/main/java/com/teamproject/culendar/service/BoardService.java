package com.teamproject.culendar.service;

import com.teamproject.culendar.domain.board.Board;
import com.teamproject.culendar.domain.enumFiles.BoardType;
import com.teamproject.culendar.dto.BoardDTO;
import com.teamproject.culendar.dto.BoardForm;
import com.teamproject.culendar.dto.PageRequestDTO;
import com.teamproject.culendar.repository.BoardRepository;
import com.teamproject.culendar.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BoardService {

  private final BoardRepository boardRepository;
  private final MemberRepository memberRepository;

  // 게시글 등록
  public Long save(BoardForm boardForm) {
    Board entity = boardForm.toEntity();
    Board savedBoard = boardRepository.save(entity);
    log.info("**** BoardService save - entity : {}", entity);

    return savedBoard.getId();
  }

  // 게시글 목록 불러오기
  public Page<Board> getListWithPaging(PageRequestDTO pageRequestDTO) {
    Pageable pageable = PageRequest.of(
        pageRequestDTO.getPage() - 1,
        pageRequestDTO.getSize(),
        Sort.by("id").descending());

    Page<Board> result = boardRepository.findAll(pageable);

    return result;
  }

  // 게시글 카테고리 목록 불러오기
  public Page<Board> getListWithCategory(PageRequestDTO pageRequestDTO, BoardType boardType){
    Pageable pageable = PageRequest.of(
        pageRequestDTO.getPage() - 1,
        pageRequestDTO.getSize(),
        Sort.by("id").descending());

    Page<Board> result = boardRepository.findByBoardType(boardType, pageable);
    return result;
  }

  // 게시글 인기순(북마크순) 목록 불러오기
  public Page<Board> getListWithBkMark(PageRequestDTO pageRequestDTO){
    Pageable pageable = PageRequest.of(
        pageRequestDTO.getPage() - 1,
        pageRequestDTO.getSize(),
        Sort.by("id").descending());

    Page<Board> result = boardRepository.findOrderByBkMark(pageable);
    return result;
  }

  // 게시글 조회
  public BoardDTO getOneBoard(Long id) {
    Board board = boardRepository.findById(id).orElse(null);
    board.setViewCount(board.getViewCount() + 1);

    //BoardDTO 의 memberDTO 를 외부(getOneBoard)에서 변환하는 경우
//    Member member = board.getMember(); // Board 에서 Member 엔티티만 꺼내기
//    MemberDTO memberDTO = new MemberDTO(member); // Member entity -> MemberDTO 로 변환
    BoardDTO boardDTO = new BoardDTO(board); // Board entity -> BoardDTO 변환
//    boardDTO.setMemberDTO(memberDTO); // BoardDTO 에 부족한 MemberDTO 를 채우기

    return boardDTO;
  }

  // 게시글 삭제
  public void deleteOneBoard(Long id) {
    boardRepository.deleteById(id);
  }

  // 게시글 수정
  public void updateOneBoard(BoardForm boardForm) {
    Board findBoard = boardRepository.findById(boardForm.getId()).orElse(null);
    findBoard.setTitle(boardForm.getTitle());
    findBoard.setContent(boardForm.getContent());
  }

  // 최근 일주일 동안 최고 인기글 4개 조회
    public List<Board> getBestList() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime weekAgo = now.minusDays(7);
        List<Board> result = boardRepository.findBestByBkMark();
        log.info("**** BoardService getBestList - result : {}", result);
        return result;
    }


}

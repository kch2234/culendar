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
import java.util.ArrayList;
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

    return savedBoard.getId();
  }

  // 전체 게시글 최신순 목록 불러오기
  public Page<Board> getListWithPaging(PageRequestDTO pageRequestDTO) {
    Pageable pageable = PageRequest.of(
        pageRequestDTO.getPage() - 1,
        pageRequestDTO.getSize(),
        Sort.by("id").descending());

    Page<Board> result = boardRepository.findAll(pageable);

    return result;
  }

  // 카테고리 게시글 최신순 목록 불러오기
  public Page<Board> getListWithCategory(PageRequestDTO pageRequestDTO, BoardType boardType){
    Pageable pageable = PageRequest.of(
        pageRequestDTO.getPage() - 1,
        pageRequestDTO.getSize(),
        Sort.by("id").descending());

    Page<Board> result = boardRepository.findByBoardType(boardType, pageable);
    return result;
  }

  // 전체 게시글 인기순 목록 불러오기
  public Page<Board> getListWithBkMark(PageRequestDTO pageRequestDTO){
    Pageable pageable = PageRequest.of(
        pageRequestDTO.getPage() - 1,
        pageRequestDTO.getSize(),
        Sort.by("id").descending());

    Page<Board> result = boardRepository.findOrderByBkMark(pageable);
    return result;
  }

  // 카테고리 게시글 인기순 목록 불러오기
  public Page<Board> getCategoryListWithBkMark(PageRequestDTO pageRequestDTO, BoardType boardType){
    log.info("**** BoardService getCategoryListWithBkMark - /{}/BEST/1", boardType);
    Pageable pageable = PageRequest.of(
        pageRequestDTO.getPage() - 1,
        pageRequestDTO.getSize(),
        Sort.by("id").descending());

    Page<Board> result = boardRepository.findOrderByBoardType(boardType, pageable);
    return result;
  }

  // 게시글 조회
  public BoardDTO getOneBoard(Long id) {
    Board board = boardRepository.findById(id).orElse(null);
    board.setViewCount(board.getViewCount() + 1);

    BoardDTO boardDTO = new BoardDTO(board); // Board entity -> BoardDTO 변환
    log.info("**** BoardService getOneBoard - boardDTO : {}", boardDTO);

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
        List<Board> result = boardRepository.findBestByBkMark();
        // 리스트 4개만 남기기
        if(result.size() > 4) {
            result = result.subList(0, 4);
        }
        log.info("**** BoardService getBestList - result : {}", result);
        return result;
    }

    // 최근 일주일 동안 해당 작품의 최고 인기 리뷰글 4개 조회
    public List<Board> getBestReviewList(Long id) {
        List<Board> result = boardRepository.findBestReviewByBkMark(id);
        // 리스트 4개만 남기기
        if(result.size() > 4) {
            result = result.subList(0, 4);
        }
        log.info("**** BoardService getBestReviewList - result : {}", result);
        return result;
    }

    // 특정 작품에 대해 작성된 회원의 리뷰글 조회
    public Boolean findProgramReviewByMemberId(Long memberId, Long programId) {
        Board result = boardRepository.findProgramReviewByMemberId(memberId, programId);

        if (result == null) {
            log.info("**** BoardService findProgramReviewByMemberId - result : null");
            return false;
        } else {
            log.info("**** BoardService findProgramReviewByMemberId - result : {}", result);
            return true;
        }
    }

    // 특정 작품에 대해 작성된 회원의 리뷰글 조회
    public BoardDTO findProgramIdReviewByMemberId(Long memberId, Long programId) {
        Board programReviewByMemberId = boardRepository.findProgramReviewByMemberId(memberId, programId);

        return new BoardDTO(programReviewByMemberId);


    }

    // 회원 리뷰 최신순으로 무한 스크롤 조회
    public List<BoardDTO> findProgramReviewByMemberIdWithPaging(Long memberId, Long start) {
        List<Board> programReviewByMemberId = boardRepository.findProgramReviewByMemberIdWithPaging(memberId, start);

        // Board entity -> BoardDTO 변환
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for (Board board : programReviewByMemberId) {
            boardDTOList.add(new BoardDTO(board));
        }
        log.info("**** BoardService findProgramReviewByMemberIdWithPaging - boardDTOList : {}", boardDTOList);

        return boardDTOList;
    }

    // 회원 정보 게시글 최신순으로 무한 스크롤 조회
    public List<BoardDTO> findMyInfoByMemberIdWithPaging(Long memberId, Long start) {
        List<Board> programInfoByMemberId = boardRepository.findProgramInfoByMemberIdWithPaging(memberId, start);

        // Board entity -> BoardDTO 변환
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for (Board board : programInfoByMemberId) {
            boardDTOList.add(new BoardDTO(board));
        }
        log.info("**** BoardService findMyInfoByMemberIdWithPaging - boardDTOList : {}", boardDTOList);

        return boardDTOList;
    }
}

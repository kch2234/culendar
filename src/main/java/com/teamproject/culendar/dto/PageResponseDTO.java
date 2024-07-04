package com.teamproject.culendar.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@Setter
@ToString
public class PageResponseDTO {

  private PageRequestDTO pageRequestDTO;
  private Long totalCount;
  private int startPage, endPage;
  private List<Integer> pageNumList;
  private boolean prev, next;
  private int totalPage;
  private boolean lastPage;
  private List<BoardDTO> list; // 화면에 전달해줄 글 목록
  private List<EventBoardDTO> eventList;  // 화면에 전달해줄 글 목록

  public PageResponseDTO(PageRequestDTO pageRequestDTO, Long totalCount) {
    this.pageRequestDTO = pageRequestDTO;
    this.totalCount = totalCount;

    this.endPage = (int)Math.ceil((double)pageRequestDTO.getPage() / pageRequestDTO.getSize())
        * pageRequestDTO.getSize();
    this.startPage = this.endPage - (pageRequestDTO.getSize() - 1);

    this.totalPage = (int)Math.ceil((double) this.totalCount / pageRequestDTO.getSize());
    if(this.totalPage < this.endPage) {
      this.endPage = this.totalPage;
    }
    this.prev = this.startPage > 1;
    this.next = pageRequestDTO.getPage() < this.totalPage;

    this.pageNumList = IntStream.rangeClosed(this.startPage, this.endPage).boxed().collect(Collectors.toList());

    this.lastPage = pageRequestDTO.getPage() == this.totalPage;


  }


}
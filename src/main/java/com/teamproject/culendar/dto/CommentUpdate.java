package com.teamproject.culendar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentUpdate {
  private Long id;
  private String comment;
  private MemberDTO memberDTO;
}

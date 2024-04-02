package com.teamproject.culendar.domain.board;

import com.teamproject.culendar.domain.baseEntity.BaseEntityLastModifiedDate;
import com.teamproject.culendar.domain.enumFiles.BoardType;
import com.teamproject.culendar.domain.member.Member;
import com.teamproject.culendar.domain.program.Program;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Board extends BaseEntityLastModifiedDate {
    // *****    게시글    *****

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 게시글 분류
    private BoardType boardType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id")
    private Program program;

    @Column(nullable = false)
    private String title;

    @Column(length = 4000)
    private String content;

    private Long viewCount = 0L;

    // TODO 이미지 추가
    // private String image;


}

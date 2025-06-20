package com.teamproject.culendar.domain.board;

import com.teamproject.culendar.domain.baseEntity.BaseEntityLastModifiedDate;
import com.teamproject.culendar.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Comment extends BaseEntityLastModifiedDate {
    // *****     댓글     *****

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(length = 1000, nullable = false)
    private String comment;

    // 댓글의 댓글
    private Long refId = 0L;
    private Integer step = 0;
    private Integer level = 0;
}

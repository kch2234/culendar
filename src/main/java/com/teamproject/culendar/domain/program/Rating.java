package com.teamproject.culendar.domain.program;

import com.teamproject.culendar.domain.baseEntity.BaseEntityLastModifiedDate;
import com.teamproject.culendar.domain.enumFiles.RatingType;
import com.teamproject.culendar.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Rating extends BaseEntityLastModifiedDate {
    // *****    문장형 평가    *****

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id")
    private Program program;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 문장형 평가 내용
    @Column(nullable = false)
    private Long rating;
}

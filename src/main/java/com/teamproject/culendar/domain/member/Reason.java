package com.teamproject.culendar.domain.member;

import com.teamproject.culendar.domain.enumFiles.ReasonType;
import com.teamproject.culendar.domain.baseEntity.BaseEntityCreatedDate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Reason extends BaseEntityCreatedDate {
    // *****    탈퇴 사유    *****

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 탈퇴사유 라디오값
    @Column(nullable = false)
    private ReasonType reasonType;

    // 탈퇴 사유 상세
    @Column(length = 4000)
    private String description;
}

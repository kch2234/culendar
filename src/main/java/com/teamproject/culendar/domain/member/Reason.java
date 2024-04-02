package com.teamproject.culendar.domain.member;

import com.teamproject.culendar.domain.enumfiles.ReasonList;
import com.teamproject.culendar.domain.baseEntity.BaseEntityCreatedDate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Reason extends BaseEntityCreatedDate {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 탈퇴사유 라디오값
    private ReasonList reasonList;

    // 탈퇴 사유 상세
    @Column(length = 4000)
    private String description;
}

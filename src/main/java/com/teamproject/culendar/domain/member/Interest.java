package com.teamproject.culendar.domain.member;

import com.teamproject.culendar.domain.baseEntity.BaseEntityCreatedDate;
import com.teamproject.culendar.domain.enumFiles.ProgramType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Interest extends BaseEntityCreatedDate {
    // *****    관심사 리스트   *****

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //관심사
    @Column(nullable = false)
    private ProgramType interest;
}

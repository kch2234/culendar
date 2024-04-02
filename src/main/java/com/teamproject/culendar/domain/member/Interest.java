package com.teamproject.culendar.domain.member;

import com.teamproject.culendar.domain.baseEntity.BaseEntityCreatedDate;
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

    //TODO 관심사 예정 (ENUM 작품 분류와 동일)
    //@Column(nullable = false)
    //private ProgramType interest;
}

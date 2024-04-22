package com.teamproject.culendar.domain.program;

import com.teamproject.culendar.domain.baseEntity.BaseEntityCreatedDate;
import com.teamproject.culendar.domain.enumFiles.Location;
import com.teamproject.culendar.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class ProgramBkmark extends BaseEntityCreatedDate {
    // *****    작품 북마크    *****

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id")
    private Program program;

    @Column(nullable = false)
    private Location location;
}

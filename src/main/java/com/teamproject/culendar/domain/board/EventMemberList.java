package com.teamproject.culendar.domain.board;

import com.teamproject.culendar.domain.baseEntity.BaseEntityCreatedDate;
import com.teamproject.culendar.domain.enumfiles.EventMemberStatus;
import com.teamproject.culendar.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class EventMemberList extends BaseEntityCreatedDate {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private EventBoard event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 확정 여부
    private EventMemberStatus status = EventMemberStatus.WAIT;
}

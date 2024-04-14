package com.teamproject.culendar.domain.member;

import com.teamproject.culendar.domain.baseEntity.BaseEntityCreatedDate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Getter @Setter
@Table(uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_member_follow",
                columnNames = {"member_id", "follow_id"}
        )})
public class Follow  extends BaseEntityCreatedDate {
    // *****    팔로우    *****
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follow_id")
    private Member follow;

    public Follow(Member member, Member follow) {
        this.member = member;//팔로우 하는 사람
        this.follow = follow;//팔로우 대상
    }
}

package com.teamproject.culendar.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    // 회원 아이디
    @Column(nullable = false, unique = true)
    private String username;
    // 회원 비밀번호
    @Column(nullable = false, length = 500)
    private String password;
    // 권한
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}

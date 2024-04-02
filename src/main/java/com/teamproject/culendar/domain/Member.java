package com.teamproject.culendar.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Member {
    // *****    회원    *****

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 아이디
    @Column(nullable = false, unique = true)
    private String userid;

    // 닉네임
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, length = 500)
    private String password;

}

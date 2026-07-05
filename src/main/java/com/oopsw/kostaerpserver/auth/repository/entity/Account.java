package com.oopsw.kostaerpserver.auth.repository.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Table(name = "account")
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true,  nullable = false)
    private String username; //로그인 아이디

    @Column(nullable = false)
    private String password; //BCrypt 해시

    @Column(nullable = false)
    private String role; //ROLE_USER/ROLE_MANAGER

    private String email;
    private String name;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp created;
}

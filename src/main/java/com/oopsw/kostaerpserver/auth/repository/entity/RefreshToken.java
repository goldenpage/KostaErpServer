package com.oopsw.kostaerpserver.auth.repository.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@NoArgsConstructor
@Service
@Setter
@Getter
@Builder
@Table(name = "refresh_token")
@Entity
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, length = 512)
    private String token;

    @Column(nullable = false)
    private Long expiryDate; //만료시각(ms)
}

package com.oopsw.kostaerpserver.repository.entity.purchase;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int purchaseId;

    @Column(nullable = false)
    private String foodMaterialName;

    @Column(nullable = false)
    private int foodMaterialCount;

    @Column(nullable = false)
    private int foodMaterialWeight;

    @Column(nullable = false)
    private int totalWeight;

    @Column(nullable = false)
    private int foodMaterialPrice;

    @Column(nullable = false)
    private int totalPrice;

    @Column(nullable = false)
    private String vender;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime incomeDate;

    @Column(nullable = false)
    private LocalDate expirationDate;
}

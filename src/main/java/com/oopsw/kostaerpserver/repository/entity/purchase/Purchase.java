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
@Table(name = "purchase")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id")
    private int purchaseId;

    @Column(name = "food_material_name", nullable = false)
    private String foodMaterialName;

    @Column(name = "food_material_count", nullable = false)
    private int foodMaterialCount;

    @Column(name = "food_material_weight", nullable = false)
    private int foodMaterialWeight;

    @Column(name = "total_weight", nullable = false)
    private int totalWeight;

    @Column(name = "food_material_price", nullable = false)
    private int foodMaterialPrice;

    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    @Column(name = "vender", nullable = false)
    private String vender;

    @CreationTimestamp
    @Column(name = "income_date", nullable = false)
    private LocalDateTime incomeDate;

    @Column(name = "expiration_date", nullable = false)
    private LocalDateTime expirationDate;

    @Column(name = "b_id", nullable = false)
    private String bId;
}

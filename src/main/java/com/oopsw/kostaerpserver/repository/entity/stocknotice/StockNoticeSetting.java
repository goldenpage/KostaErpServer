package com.oopsw.kostaerpserver.repository.entity.stocknotice;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Builder
@Entity
@Table(name = "stock_notice_setting")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockNoticeSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int stockNoticeSettingId;

    @Column(name = "bId", unique = true, nullable = false, length = 10)
    private String bId;

    @Column(name = "foodmAlert", nullable = false)
    private boolean foodmAlert;

    @Column(name = "foodmLimit", nullable = false)
    private int foodmLimit;

    @CreationTimestamp
    @Column(name = "createdAt", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updatedAt", nullable = false)
    private LocalDateTime updatedAt;

    public void update(boolean foodmAlert, int foodmLimit) {
        this.foodmAlert = foodmAlert;

        if (foodmLimit < 1) {
            this.foodmLimit = 1;
        } else {
            this.foodmLimit = foodmLimit;
        }
    }
}
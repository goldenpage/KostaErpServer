package com.oopsw.kostaerpserver.repository.entity.expdate;

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
@Table(name = "exp_notice")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExpNotice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exp_notice_id")
    private int expNoticeId;

    @Column(name = "b_Id", unique = true, nullable = false, length = 10)
    private String bId;

    @Column(name = "exp_Alert", nullable = false)
    private boolean expAlert;

    @Column(name = "exp_Days", nullable = false)
    private int expDays;

    @CreationTimestamp
    @Column(name = "created_At", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_At", nullable = false)
    private LocalDateTime updatedAt;

    public void update(boolean expAlert, int expDays) {
        this.expAlert = expAlert;

        if (expDays < 1) {
            this.expDays = 1;
        } else {
            this.expDays = expDays;
        }
    }
}
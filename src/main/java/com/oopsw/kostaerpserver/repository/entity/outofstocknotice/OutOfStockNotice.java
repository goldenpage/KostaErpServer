package com.oopsw.kostaerpserver.repository.entity.outofstocknotice;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class OutOfStockNotice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int noticeId;
    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime noticeDate;
    @Column(nullable = false)
    private String noticeContent;
    @Column(nullable = false)
    private String foodMaterialName;
    @Column(nullable = false)
    private int remainStockAmount;
}

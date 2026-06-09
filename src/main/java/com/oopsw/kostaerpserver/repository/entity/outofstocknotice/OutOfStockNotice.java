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
    @Column(updatable = false)
    private LocalDateTime noticeDate;
    private String noticeContent;
    private String foodMaterialName;
    private int remainStockAmount;
}

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
@Table(name = "out_of_stock_notice")
public class OutOfStockNotice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private int noticeId;

    @CreationTimestamp
    @Column(name = "notice_date", updatable = false, nullable = false)
    private LocalDateTime noticeDate;

    @Column(name = "notice_content",nullable = false)
    private String noticeContent;

    @Column(name = "food_material_name",nullable = false)
    private String foodMaterialName;

    @Column(name = "remain_stock_amount",nullable = false)
    private int remainStockAmount;

    @Column(name = "b_id",nullable = false)
    private String bId;

    @Column(name = "read_yn",nullable = false)
    @Builder.Default
    private String readYn = "N";

    public void markAsRead() {
        this.readYn = "Y";
    }
}

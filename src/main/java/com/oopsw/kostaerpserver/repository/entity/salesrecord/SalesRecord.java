package com.oopsw.kostaerpserver.repository.entity.salesrecord;

import jakarta.persistence.*;
        import lombok.*;

@Entity
@Table(name = "SALES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalesRecord {

    @Id
    @Column(name = "sale_Id")
    private String saleId;

    @Column(name = "saleMenuCount")
    private int saleMenuCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_Id")
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "revenue_Id")
    private Revenue revenue;
}
package com.oopsw.kostaerpserver.repository.entity.salesrecord;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "REVENUE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Revenue {

    @Id
    @Column(name = "revenue_Id")
    private String revenueId;

    @Column(name = "revenueDate")
    private LocalDate revenueDate;
}
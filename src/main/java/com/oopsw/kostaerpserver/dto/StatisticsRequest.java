package com.oopsw.kostaerpserver.dto;


import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class StatisticsRequest {
    private LocalDate startDate;
    private LocalDate endDate;
}

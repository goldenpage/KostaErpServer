package com.oopsw.kostaerpserver.repository.entity.salesrecord;

import com.oopsw.kostaerpserver.repository.entity.salesrecord.SalesRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SalesRecordRepository
        extends JpaRepository<SalesRecord, String> {

    List<SalesRecord> findByRevenue_RevenueDateBetween(
            LocalDate startDate,
            LocalDate endDate
    );
}

package com.oopsw.kostaerpserver.repository.entity.salesrecord;

import com.oopsw.kostaerpserver.repository.entity.salesrecord.SalesRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SalesRecordRepository
        extends JpaRepository<SalesRecord, String> {

    List<SalesRecord> findByRevenue_RevenueDateBetween(
            LocalDate start,
            LocalDate end
    );

    @Query(value = """
        SELECT CONCAT(
            'S',
            LPAD(COALESCE(MAX(CAST(SUBSTRING(sale_Id,2) AS UNSIGNED)), 0) + 1, 3, '0'
            )
        )
        FROM SALES
        """, nativeQuery = true)
    String getNextSaleId();

    @Query("SELECT s FROM SalesRecord s JOIN FETCH s.menu JOIN FETCH s.revenue ORDER BY s.saleId ASC")
    List<SalesRecord> findAllWithFetch();

    @Query("""
select s
from SalesRecord s
join s.revenue r
where r.revenueDate >= :start
and r.revenueDate <= :end
""")
    List<SalesRecord> findByDateWith(
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );
}

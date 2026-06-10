package com.oopsw.kostaerpserver.repository.entity.salesrecord;

import com.oopsw.kostaerpserver.repository.entity.salesrecord.SalesRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SalesRecordRepository
        extends JpaRepository<SalesRecord, String> {

    List<SalesRecord> findByRevenue_RevenueDateBetween(
            LocalDate startDate,
            LocalDate endDate
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

    @Query("""
            select s
            from SalesRecord s
            join fetch s.menu
            join fetch s.revenue
        """)
    List<SalesRecord> findAllWithFetch();

    @Query("""
    select s
    from SalesRecord s
    join fetch s.menu
    join fetch s.revenue
    where s.revenue.revenueDate between :start and :end
""")
    List<SalesRecord> findByDateWithFetch(
            @org.springframework.data.repository.query.Param("start") LocalDate start,
            @org.springframework.data.repository.query.Param("end") LocalDate end
    );
}

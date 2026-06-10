package com.oopsw.kostaerpserver.repository.entity.salesrecord;

import com.oopsw.kostaerpserver.repository.entity.salesrecord.Revenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RevenueRepository
        extends JpaRepository<Revenue, String> {
    @Query(value = """
            SELECT CONCAT('RV',
            LPAD(COALESCE(MAX(CAST(SUBSTRING(revenue_id,3) AS UNSIGNED)),0)+1,3,'0'))
            FROM REVENUE
        """, nativeQuery = true)
    String getNextRevenueId();
}

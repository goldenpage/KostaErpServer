package com.oopsw.kostaerpserver.repository.entity.salesrecord;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Collection;
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

    @Query(
            value = """
                SELECT s
                FROM SalesRecord s
                LEFT JOIN FETCH s.menu m
                LEFT JOIN FETCH m.menuCategory
                LEFT JOIN FETCH s.revenue
                ORDER BY s.saleId DESC
                """,
            countQuery = """
                SELECT COUNT(s)
                FROM SalesRecord s
                """
    )
    Page<SalesRecord> findAllWithFetch(Pageable pageable);

    @Query(
            value = """
        select s
        from SalesRecord s
        join fetch s.menu m
        join fetch m.menuCategory mc
        join fetch s.revenue r
        where (:start is null or r.revenueDate >= :start)
          and (:end is null or r.revenueDate <= :end)
          and (:category is null or mc.menuCategory = :category)
          and (:menuName is null or m.menuName = :menuName)
          and (:payment is null or r.payment = :payment)
        order by s.saleId desc
        """,
            countQuery = """
        select count(s)
        from SalesRecord s
        join s.menu m
        join m.menuCategory mc
        join s.revenue r
        where (:start is null or r.revenueDate >= :start)
          and (:end is null or r.revenueDate <= :end)
          and (:category is null or mc.menuCategory = :category)
          and (:menuName is null or m.menuName = :menuName)
          and (:payment is null or r.payment = :payment)
        """
    )
    Page<SalesRecord> searchSales(
            @Param("start") LocalDate start,
            @Param("end") LocalDate end,
            @Param("category") String category,
            @Param("menuName") String menuName,
            @Param("payment") String payment,
            Pageable pageable
    );

    @Query("SELECT SUM(s.saleMenuCount * m.menuPrice) FROM SalesRecord s JOIN s.menu m")
    Integer getTotalRevenue();

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

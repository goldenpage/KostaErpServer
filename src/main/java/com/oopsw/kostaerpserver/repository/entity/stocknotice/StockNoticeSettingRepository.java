package com.oopsw.kostaerpserver.repository.entity.stocknotice;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StockNoticeSettingRepository extends JpaRepository<StockNoticeSetting, Integer> {
    @Query("select s from StockNoticeSetting s where s.bId = :bId")
    Optional<StockNoticeSetting> findByBId(@Param("bId") String bId);
}
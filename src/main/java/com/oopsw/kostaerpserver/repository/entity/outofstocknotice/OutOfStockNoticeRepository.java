package com.oopsw.kostaerpserver.repository.entity.outofstocknotice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutOfStockNoticeRepository extends JpaRepository<OutOfStockNotice, Integer> {
    // 읽지 않은 알림 목록 조회 (최신순)
    @Query("SELECT o FROM OutOfStockNotice o WHERE o.bId = :bId AND o.readYn = :readYn ORDER BY o.noticeDate DESC")
    List<OutOfStockNotice> findByBIdAndReadYnOrderByNoticeDateDesc(String bId, String readYn);

    // 읽지 않은 알림 개수 (헤더 배지용)
    @Query("SELECT COUNT(o) FROM OutOfStockNotice o WHERE o.bId = :bId AND o.readYn = :readYn")
    int countByBIdAndReadYn(String bId, String readYn);

    // 중복 저장 방지 — 동일 식자재의 읽지 않은 알림이 이미 있는지 확인
    @Query("SELECT COUNT(o) > 0 FROM OutOfStockNotice o WHERE o.bId = :bId AND o.foodMaterialName = :foodMaterialName AND o.readYn = :readYn")
    boolean existsByBIdAndFoodMaterialNameAndReadYn(String bId, String foodMaterialName, String readYn);

    // 특정 사업장 전체 읽음 처리
    @Modifying
    @Query("UPDATE OutOfStockNotice o SET o.readYn = 'Y' WHERE o.bId = :bId AND o.readYn = 'N'")
    int markAllAsRead(@Param("bId") String bId);
}

package com.oopsw.kostaerpserver.repository.entity.outofstocknotice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutOfStockNoticeRepository extends JpaRepository<OutOfStockNotice, Integer> {

    @Query("SELECT o FROM OutOfStockNotice o WHERE o.bId = :bId AND o.readYn = :readYn ORDER BY o.noticeDate DESC")
    List<OutOfStockNotice> findByBIdAndReadYnOrderByNoticeDateDesc(@Param("bId") String bId, @Param("readYn") String readYn);

    @Query("SELECT COUNT(o) FROM OutOfStockNotice o WHERE o.bId = :bId AND o.readYn = :readYn")
    int countByBIdAndReadYn(@Param("bId") String bId, @Param("readYn") String readYn);

    @Query("SELECT COUNT(o) > 0 FROM OutOfStockNotice o " +
            "WHERE o.bId = :bId " +
            "AND o.foodMaterialName = :foodMaterialName " +
            "AND o.readYn = 'N' " +
            "AND FUNCTION('DATE', o.noticeDate) = FUNCTION('DATE', CURRENT_TIMESTAMP)")
    boolean existsTodayNotice(@Param("bId") String bId, @Param("foodMaterialName") String foodMaterialName);
}

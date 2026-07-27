package com.oopsw.kostaerpserver.repository.entity.expdate;

import java.util.Optional;

import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpNoticeRepository extends JpaRepository<ExpNotice, Integer> {
    @Query("select e from ExpNotice e where e.bId = :bId")
    Optional<ExpNotice> findByBId(@Param("bId") String bId);
}
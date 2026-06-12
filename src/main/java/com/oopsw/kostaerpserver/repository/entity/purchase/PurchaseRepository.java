package com.oopsw.kostaerpserver.repository.entity.purchase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
    @Query("SELECT p FROM Purchase p WHERE p.bId = :bId ORDER BY p.incomeDate DESC")
    List<Purchase> findAllByBIdOrderByIncomeDateDesc(String bId);
}

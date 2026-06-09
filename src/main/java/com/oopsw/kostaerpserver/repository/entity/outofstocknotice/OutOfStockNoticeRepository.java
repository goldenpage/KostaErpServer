package com.oopsw.kostaerpserver.repository.entity.outofstocknotice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutOfStockNoticeRepository extends JpaRepository<OutOfStockNotice, Integer> {

}

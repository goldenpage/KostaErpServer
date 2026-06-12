package com.oopsw.kostaerpserver.service.entity.purchase;

import com.oopsw.kostaerpserver.repository.entity.outofstocknotice.OutOfStockNotice;
import com.oopsw.kostaerpserver.repository.entity.purchase.Purchase;
import com.oopsw.kostaerpserver.repository.entity.purchase.PurchaseRepository;
import com.oopsw.kostaerpserver.vo.entity.PurchaseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {
    private final PurchaseRepository purchaseRepository;

    @Override
    public boolean addPurchase(PurchaseVO vo){
        Purchase notice = purchaseRepository.save(Purchase.builder().
                foodMaterialName("김치").
                foodMaterialCount(5).
                foodMaterialWeight(5000).
                totalWeight(25000).
                foodMaterialPrice(20000).
                totalPrice(100000).
                vender("김치집").
                incomeDate(LocalDateTime.now()).
                expirationDate(LocalDate.parse("2030-06-09")).
                build());

        return notice != null;
    }
}

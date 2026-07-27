package com.oopsw.kostaerpserver.service.entity.purchase;

import com.oopsw.kostaerpserver.repository.entity.purchase.Purchase;
import com.oopsw.kostaerpserver.repository.entity.purchase.PurchaseRepository;
import com.oopsw.kostaerpserver.vo.entity.PurchaseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {
    private final PurchaseRepository purchaseRepository;

    @Override
    public boolean addPurchase(PurchaseVO vo){
        int totalWeight = vo.getFoodMaterialWeight() * vo.getFoodMaterialCount();
        int totalPrice = vo.getFoodMaterialPrice() * vo.getFoodMaterialCount();
        LocalDateTime incomeDate = (vo.getIncomeDate() == null) ? LocalDateTime.now() : vo.getIncomeDate();
        LocalDateTime expirationDate = (vo.getExpirationDate() == null) ? LocalDateTime.now() : vo.getExpirationDate();

        Purchase purchase = purchaseRepository.save(Purchase.builder().
                foodMaterialName(vo.getFoodMaterialName()).
                foodMaterialCount(vo.getFoodMaterialCount()).
                foodMaterialWeight(vo.getFoodMaterialWeight()).
                totalWeight(totalWeight).
                foodMaterialPrice(vo.getFoodMaterialPrice()).
                totalPrice(totalPrice).
                vender(vo.getVender()).
                incomeDate(incomeDate).
                expirationDate(expirationDate).
                bId(vo.getBId()).
                build());

        return purchase != null;
    }

    @Override
    public List<Purchase> getPurchaseList(String bId) {
        return purchaseRepository.findAllByBIdOrderByIncomeDateDesc(bId);
    }
}

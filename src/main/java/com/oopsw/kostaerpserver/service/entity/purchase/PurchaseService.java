package com.oopsw.kostaerpserver.service.entity.purchase;

import com.oopsw.kostaerpserver.repository.entity.purchase.Purchase;
import com.oopsw.kostaerpserver.vo.entity.PurchaseVO;

import java.util.List;

public interface PurchaseService {
    boolean addPurchase(PurchaseVO vo);
    List<Purchase> getPurchaseList(String bId);
}

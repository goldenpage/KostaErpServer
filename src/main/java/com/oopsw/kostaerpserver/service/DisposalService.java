package com.oopsw.kostaerpserver.service;

import com.oopsw.kostaerpserver.vo.Disposal;
import java.time.LocalDate;
import java.util.List;

public interface DisposalService {
    List<Disposal> getDisposals();

    List<String> getFoodMaterialNames();

    List<String> getCategories();

    List<Disposal> getDisposalsFilteredPaging(String bId, int page, int size);

    int getDisposalCount(String bId);

    int getTotalCount(String bId);

    List<String> getReasons();

    List<Disposal> getDisposalsByCategoryAndBId(String category, String bId);

    List<Disposal> getDisposalsPaging(String bId, int page, int size);

    boolean updateReason(String disposalId, String reasonId);

    List<String> getExpiredDisposalIds(String bId);

    double getDisposalRate(String bId, LocalDate startDate, LocalDate endDate);

    int getTotalDisposalPrice(String bId, LocalDate startDate, LocalDate endDate);

    List<Disposal> getTop3DisposalItems(String bId, LocalDate startDate, LocalDate endDate);

    List<Disposal> getDisposalReasonRatio(String bId, LocalDate startDate, LocalDate endDate);

    List<Disposal> selectDailyDisposalAmount(String bId, LocalDate startDate, LocalDate endDate);

    List<Disposal> selectDailyDisposalByType(String bId, LocalDate startDate, LocalDate endDate);
}
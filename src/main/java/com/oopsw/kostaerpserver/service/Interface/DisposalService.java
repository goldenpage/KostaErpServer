package com.oopsw.kostaerpserver.service.Interface;

import com.oopsw.kostaerpserver.dto.disposal.DisposalCreateRequest;
import com.oopsw.kostaerpserver.vo.Disposal;
import com.oopsw.kostaerpserver.dto.disposal.DisposalListResponse;
import java.time.LocalDate;
import java.util.List;

public interface DisposalService {
    List<DisposalListResponse> getDisposals();

    List<String> getFoodMaterialNames();
    List<String> getCategories(String bId);
    List<String> getReasons();

    List<DisposalListResponse> getDisposalsFilteredPaging(String bId, int page, int size);
    List<DisposalListResponse> getDisposalsByCategoryAndBId(String category, String bId);
    List<DisposalListResponse> getDisposalsPaging(String bId, int page, int size);

    int getDisposalCount(String bId);
    int getTotalCount(String bId);
    boolean updateReason(String disposalId, String reasonId);
    boolean insertDisposal(DisposalCreateRequest request);
    List<String> getExpiredDisposalIds(String bId);
    double getDisposalRate(String bId, LocalDate startDate, LocalDate endDate);
    int getTotalDisposalPrice(String bId, LocalDate startDate, LocalDate endDate);

    List<Disposal> getTop3DisposalItems(String bId, LocalDate startDate, LocalDate endDate);
    List<Disposal> getDisposalReasonRatio(String bId, LocalDate startDate, LocalDate endDate);
    List<Disposal> selectDailyDisposalAmount(String bId, LocalDate startDate, LocalDate endDate);
    List<Disposal> selectDailyDisposalByType(String bId, LocalDate startDate, LocalDate endDate);
}
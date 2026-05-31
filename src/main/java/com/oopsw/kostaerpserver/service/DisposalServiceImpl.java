package com.oopsw.kostaerpserver.service;

import com.oopsw.kostaerpserver.repository.DisposalDAO;
import com.oopsw.kostaerpserver.service.Interface.DisposalService;
import com.oopsw.kostaerpserver.vo.Disposal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DisposalServiceImpl implements DisposalService {

    private final DisposalDAO disposalDAO;

    @Override
    public List<Disposal> getDisposals() {
        return disposalDAO.getDisposals();
    }

    @Override
    public List<String> getFoodMaterialNames() {
        return disposalDAO.getFoodMaterialNames();
    }

    @Override
    public List<String> getCategories() {
        return disposalDAO.getCategories();
    }

    @Override
    public List<Disposal> getDisposalsFilteredPaging(String bId, int page, int size) {
        return disposalDAO.getDisposalsFilteredPaging(bId, toOffset(page, size), size);
    }

    @Override
    public int getDisposalCount(String bId) {
        return disposalDAO.getDisposalCount(bId);
    }

    @Override
    public int getTotalCount(String bId) {
        return disposalDAO.getTotalCount(bId);
    }

    @Override
    public List<String> getReasons() {
        return disposalDAO.getReasons();
    }

    @Override
    public List<Disposal> getDisposalsByCategoryAndBId(String category, String bId) {
        return disposalDAO.getDisposalsByCategoryAndBId(category, bId);
    }

    @Override
    public List<Disposal> getDisposalsPaging(String bId, int page, int size) {
        return disposalDAO.getDisposalsPaging(bId, toOffset(page, size), size);
    }

    @Override
    @Transactional //수정 작업을 위해 트랜잭션으로 오버라이딩
    public boolean updateReason(String disposalId, String reasonId) {
        return disposalDAO.updateReason(disposalId, reasonId) == 1;
    }

    @Override
    public List<String> getExpiredDisposalIds(String bId) {
        return disposalDAO.getExpiredDisposalIds(bId);
    }

    @Override
    public double getDisposalRate(String bId, LocalDate startDate, LocalDate endDate) {
        return disposalDAO.getDisposalRate(bId, startDate, endDate);
    }

    @Override
    public int getTotalDisposalPrice(String bId, LocalDate startDate, LocalDate endDate) {
        return disposalDAO.getTotalDisposalPrice(bId, startDate, endDate);
    }

    @Override
    public List<Disposal> getTop3DisposalItems(String bId, LocalDate startDate, LocalDate endDate) {
        return disposalDAO.getTop3DisposalItems(bId, startDate, endDate);
    }

    @Override
    public List<Disposal> getDisposalReasonRatio(String bId, LocalDate startDate, LocalDate endDate) {
        return disposalDAO.getDisposalReasonRatio(bId, startDate, endDate);
    }

    @Override
    public List<Disposal> selectDailyDisposalAmount(String bId, LocalDate startDate, LocalDate endDate) {
        return disposalDAO.selectDailyDisposalAmount(bId, startDate, endDate);
    }

    @Override
    public List<Disposal> selectDailyDisposalByType(String bId, LocalDate startDate, LocalDate endDate) {
        return disposalDAO.selectDailyDisposalByType(bId, startDate, endDate);
    }

    private int toOffset(int page, int size) {
        return Math.max(page - 1, 0) * size;
    }
}
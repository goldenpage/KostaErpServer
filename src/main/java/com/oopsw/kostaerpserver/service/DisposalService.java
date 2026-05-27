package com.oopsw.kostaerpserver.service;

import com.oopsw.kostaerpserver.repository.DisposalDAO;
import com.oopsw.kostaerpserver.vo.DisposalVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class DisposalService {
    private final DisposalDAO disposalDAO;
    public DisposalService(DisposalDAO disposalDAO) {
        this.disposalDAO = disposalDAO;
    }

    public List<DisposalVO> getDisposals(){
        return disposalDAO.getDisposals();
    }

    public List<String> getFoodMaterialNames(){
        return disposalDAO.getFoodMaterialNames();
    }

    public List<String> getCategories(){
        return disposalDAO.getCategories();
    }

    public List<DisposalVO> getDisposalsFilteredPaging(String bId, int page, int size){
        return disposalDAO.getDisposalsFilteredPaging(bId, toOffset(page, size), size);
    }

    public int getDisposalCount(String bId){
        return disposalDAO.getDisposalCount(bId);
    }

    public int getTotalCount(String bId){
        return disposalDAO.getTotalCount(bId);
    }

    public List<String> getReasons(){
        return disposalDAO.getReasons();
    }

    public List<DisposalVO> getDisposalsByCategoryAndBId(String category, String bId){
        return disposalDAO.getDisposalsByCategoryAndBId(category, bId);
    }

    public List<DisposalVO> getDisposalsPaging(String bId, int page, int size){
        return disposalDAO.getDisposalsPaging(bId, toOffset(page, size), size);
    }

    @Transactional
    public boolean updateReason(String disposalId, String reasonId){
        return disposalDAO.updateReason(disposalId, reasonId) == 1;
    }

    public List<String> getExpiredDisposalIds(String bId){
        return disposalDAO.getExpiredDisposalIds(bId);
    }

    public double getDisposalRate(String bId, LocalDate startDate, LocalDate endDate){
        return disposalDAO.getDisposalRate(bId, startDate, endDate);
    }

    public int getTotalDisposalPrice(String bId, LocalDate startDate, LocalDate endDate){
        return disposalDAO.getTotalDisposalPrice(bId, startDate, endDate);
    }

    public List<DisposalVO> getTop3DisposalItems(String bId, LocalDate startDate, LocalDate endDate){
        return disposalDAO.getTop3DisposalItems(bId, startDate, endDate);
    }

    public List<DisposalVO> getDisposalReasonRatio(String bId, LocalDate startDate, LocalDate endDate){
        return disposalDAO.getDisposalReasonRatio(bId, startDate, endDate);
    }

    public List<DisposalVO> selectDailyDisposalAmount(String bId, LocalDate startDate, LocalDate endDate){
        return disposalDAO.selectDailyDisposalAmount(bId, startDate, endDate);
    }

    public List<DisposalVO> selectDailyDisposalByType(String bId, LocalDate startDate, LocalDate endDate){
        return disposalDAO.selectDailyDisposalByType(bId, startDate, endDate);
    }

    private int toOffset(int page, int size) {
        return Math.max(page - 1, 0) * size;
    }
}

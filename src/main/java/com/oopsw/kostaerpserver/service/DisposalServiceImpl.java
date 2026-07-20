package com.oopsw.kostaerpserver.service;

import com.oopsw.kostaerpserver.dto.disposal.DisposalCreateRequest;
import com.oopsw.kostaerpserver.repository.dao.DisposalDAO;
import com.oopsw.kostaerpserver.service.Interface.DisposalService;
import com.oopsw.kostaerpserver.vo.Disposal;
import com.oopsw.kostaerpserver.dto.disposal.DisposalListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DisposalServiceImpl implements DisposalService {

    private final DisposalDAO disposalDAO;

    @Override
    public List<DisposalListResponse> getDisposals() {
        return disposalDAO.getDisposals();
    }

    @Override
    public List<String> getFoodMaterialNames() {
        return disposalDAO.getFoodMaterialNames();
    }

    @Override
    public List<String> getCategories(String bId) {
        return disposalDAO.getCategories(bId);
    }

    @Override
    public List<DisposalListResponse> getDisposalsFilteredPaging(String bId, int page, int size) {
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
    public List<DisposalListResponse> getDisposalsByCategoryAndBId(String category, String bId) {
        return disposalDAO.getDisposalsByCategoryAndBId(category, bId);
    }

    @Override
    public List<DisposalListResponse> getDisposalsPaging(String bId, int page, int size) {
        return disposalDAO.getDisposalsPaging(bId, toOffset(page, size), size);
    }

    @Override
    @Transactional //수정 작업을 위해 트랜잭션으로 오버라이딩
    public boolean updateReason(String disposalId, String reasonId) {
        return disposalDAO.updateReason(disposalId, reasonId) == 1;
    }

    @Override
    @Transactional
    public boolean insertDisposal(DisposalCreateRequest request, String bId) {
        if (request == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "폐기 등록 정보가 필요합니다."
            );
        }

        if (request.getFoodMaterialId() == null || request.getFoodMaterialId().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "식자재 ID가 필요합니다."
            );
        }

        if (request.getReasonId() == null || request.getReasonId().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "폐기 사유가 필요합니다."
            );
        }

        if (request.getDisposalDate() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "폐기일이 필요합니다."
            );
        }
        if (request.getDisposalCountAll() <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "폐기량은 0보다 커야합니다."
            );
        }
        if (request.getDisposalPrice() < 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "폐기금액은 0 이상이어야 합니다."
            );
        }

        int updated = disposalDAO.decreaseTotalWeight(
                request.getFoodMaterialId(),
                bId,
                request.getDisposalCountAll()
        );

        if (updated != 1){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "식자재가 없거나 폐기량이 현재 재고보다 많습니다."
            );
        }

        int inserted = disposalDAO.insertDisposal(request);
        if (inserted != 1) {
            throw new IllegalStateException("폐기 내역 등록에 실패했습니다.");
        }
        return true;
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
package com.oopsw.kostaerpserver.service.entity.salesrecord;

import com.oopsw.kostaerpserver.dto.salesrecord.SalesRecordResponse;
import com.oopsw.kostaerpserver.repository.entity.salesrecord.*;
import com.oopsw.kostaerpserver.service.Interface.MenuService;
import javax.sql.DataSource;
import java.sql.Connection;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SalesRecordServiceImpl implements SalesRecordService {
    private final MenuRepository menuRepository;
    private final SalesRecordRepository salesRecordRepository;
    private final RevenueRepository revenueRepository;
    private final MenuService menuService;
    private final DataSource dataSource;

    @Autowired
    private EntityManager entityManager;

    @Override
    public Page<SalesRecordResponse> getSalesList(int page, int size){
        int safePage = Math.max(0, page);

        Pageable pageable = PageRequest.of(safePage, size, Sort.by("saleId").ascending());
        List<SalesRecord> content = salesRecordRepository.findAllWithFetch();
        List<SalesRecordResponse> dtoList = content.stream().map(this::toDTO).toList();

        return new PageImpl<>(dtoList, pageable, dtoList.size());
    }

    @Override
    @Transactional
    public void addSale(String menuId, int saleMenuCount, String bId, String payment){
        try (Connection conn = dataSource.getConnection()) {
            System.out.println(">>> 현재 접속된 DB URL: " + conn.getMetaData().getURL());
            System.out.println(">>> 현재 접속된 DB 이름: " + conn.getCatalog());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String trimmedMenuId = (menuId != null) ? menuId.trim() : "";
        Menu menu = menuRepository.findById(trimmedMenuId).
                orElseThrow(
                        () -> {
                            return new RuntimeException("메뉴를 찾을 수 없습니다: " + trimmedMenuId);
                        });

        Revenue revenue = new Revenue();
        String newId = revenueRepository.getNextRevenueId();
        revenue.setRevenueId(newId);
        revenue.setRevenueDate(LocalDate.now());
        revenue.setPayment(payment);

        revenueRepository.saveAndFlush(revenue);

        entityManager.clear();
        if (revenueRepository.findById(newId).isEmpty()) {
            throw new RuntimeException("REVENUE 저장 실패: DB에 데이터가 기록되지 않음");
        }
        SalesRecord salesRecord = new SalesRecord();
        salesRecord.setSaleId(salesRecordRepository.getNextSaleId());
        salesRecord.setMenu(menu);
        salesRecord.setRevenue(revenue);
        salesRecord.setSaleMenuCount(saleMenuCount);

        salesRecordRepository.saveAndFlush(salesRecord);

        menuService.saleMenu(menuId, saleMenuCount, bId, payment);
    }

    @Override
    public List<SalesRecordResponse> getSalesByDate(String startDate, String endDate){
        if (startDate == null || startDate.isEmpty() || endDate == null || endDate.isEmpty()) {
            return salesRecordRepository.findAllWithFetch().stream().map(this::toDTO).toList();
        }

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        return salesRecordRepository.findByDateWith(start, end)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public void deleteSale(String saleId) {
        SalesRecord record = salesRecordRepository.findById(saleId)
                .orElseThrow(() -> new RuntimeException("판매 기록을 찾을 수 없습니다."));
        Revenue revenue = record.getRevenue();
        salesRecordRepository.delete(record);
        if (revenue != null) {
            revenueRepository.delete(revenue);
        }

    }

    @Override
    public SalesRecordResponse getSalesById(String id) {
        SalesRecord record = salesRecordRepository.findById(id)
                .orElse(null);
        if (record == null) {
            return new SalesRecordResponse();
        }

        return toDTO(record);
    }

    @Override
    @Transactional
    public void updateSale(String salesId, String menuId, int saleMenuCount, String payment) {
        SalesRecord record = salesRecordRepository.findById(salesId)
                .orElseThrow(() -> new RuntimeException("판매 기록을 찾을 수 없습니다."));
        record.setSaleMenuCount(saleMenuCount);
        if (record.getRevenue() != null) {
            record.getRevenue().setPayment(payment);
        }
    }

    @Override
    public int getTotalRevenue() {
        Integer total = salesRecordRepository.getTotalRevenue();
        return total != null ? total : 0;
    }

    //Entity > DTO 변환 메서드
    private SalesRecordResponse toDTO(SalesRecord salesRecord) {
        SalesRecordResponse dto = new SalesRecordResponse();
        dto.setSaleId(salesRecord.getSaleId());

        if (salesRecord.getRevenue() != null) {
            try {
                dto.setRevenueId(salesRecord.getRevenue().getRevenueId());
                if (salesRecord.getRevenue().getRevenueDate() != null) {
                    dto.setSaleDate(salesRecord.getRevenue().getRevenueDate().toString());
                }
                dto.setPaymentMethod(salesRecord.getRevenue().getPayment());
            } catch (Exception e) {
                dto.setSaleDate("데이터없음");
            }
        }

        if (salesRecord.getMenu() != null) {
            dto.setMenuName(salesRecord.getMenu().getMenuName());
            dto.setCategory(salesRecord.getMenu().getMenuCategory() != null
                    ? salesRecord.getMenu().getMenuCategory().getMenuCategory() : null);
            dto.setPrice(salesRecord.getMenu().getMenuPrice());
        }

        dto.setQty(salesRecord.getSaleMenuCount());
        dto.setTotalPrice(salesRecord.getSaleMenuCount() * (salesRecord.getMenu() != null ? salesRecord.getMenu().getMenuPrice() : 0));

        return dto;
    }
}

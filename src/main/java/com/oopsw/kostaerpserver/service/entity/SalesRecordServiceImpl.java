package com.oopsw.kostaerpserver.service.entity;

import com.oopsw.kostaerpserver.dto.salesrecord.SalesRecordResponse;
import com.oopsw.kostaerpserver.repository.entity.salesrecord.*;
import com.oopsw.kostaerpserver.service.Interface.MenuService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Page<SalesRecordResponse> getSalesList(int page, int size){
        Pageable pageable = PageRequest.of(page - 1, size);
        List<SalesRecord> content = salesRecordRepository.findAllWithFetch();

        List<SalesRecordResponse> dtoList =
                content.stream().map(this::toDTO).toList();

        return new PageImpl<>(dtoList, pageable, content.size());
    }

    @Override
    @Transactional
    public void addSale(String menuId, int saleMenuCount, String bId, String payment){
        Menu menu = menuRepository.findById(menuId).
                orElseThrow(
                        () -> new RuntimeException("메뉴를 찾을 수 없습니다."));

        Revenue revenue = new Revenue();
        revenue.setRevenueId(revenueRepository.getNextRevenueId());
        revenue.setRevenueDate(LocalDate.now());
        revenue.setPayment(payment);
        revenue = revenueRepository.save(revenue);

        SalesRecord salesRecord = new SalesRecord();
        salesRecord.setSaleId(salesRecordRepository.getNextSaleId());
        salesRecord.setMenu(menu);
        salesRecord.setRevenue(revenue);
        salesRecord.setSaleMenuCount(saleMenuCount);
        salesRecordRepository.save(salesRecord);

        menuService.saleMenu(menuId, saleMenuCount, bId);
    }

    @Override
    public List<SalesRecordResponse> getSalesByDate(String startDate, String endDate){
        return salesRecordRepository
                .findByDateWithFetch(
                        LocalDate.parse(startDate),
                        LocalDate.parse(endDate)
                )
                .stream()
                .map(this::toDTO)
                .toList();
    }

    //Entity > DTO 변환 메서드
    private SalesRecordResponse toDTO(SalesRecord salesRecord) {

        SalesRecordResponse dto = new SalesRecordResponse();

        dto.setSaleId(salesRecord.getSaleId());

        dto.setRevenueId(salesRecord.getRevenue().getRevenueId());
        dto.setSaleDate(salesRecord.getRevenue().getRevenueDate().toString());
        dto.setPaymentMethod(salesRecord.getRevenue().getPayment());

        dto.setMenuName(salesRecord.getMenu().getMenuName());
        dto.setCategory(salesRecord.getMenu().getMenuCategory() != null
                ? salesRecord.getMenu().getMenuCategory().getMenuCategory() : null);
        dto.setPrice(salesRecord.getMenu().getMenuPrice());

        dto.setQty(salesRecord.getSaleMenuCount());
        dto.setTotalPrice(salesRecord.getSaleMenuCount() * salesRecord.getMenu().getMenuPrice());

        return dto;
    }
}

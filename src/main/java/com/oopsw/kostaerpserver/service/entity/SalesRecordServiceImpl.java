package com.oopsw.kostaerpserver.service.entity;

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

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
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
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("saleId").ascending());
        List<SalesRecord> content = salesRecordRepository.findAllWithFetch();

        List<SalesRecordResponse> dtoList =
                content.stream().map(this::toDTO).toList();

        return new PageImpl<>(dtoList, pageable, content.size());
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

        // 2. 로그를 찍어서 실제 어떤 값이 들어오는지 확인합니다 (디버깅용)
        System.out.println(">>> 조회 시도할 ID: [" + trimmedMenuId + "]");

        Menu menu = menuRepository.findById(trimmedMenuId).
                orElseThrow(
                        () -> {
                            // 3. 에러 발생 시 어떤 ID로 찾다가 실패했는지 명시하면 원인 파악이 쉽습니다.
                            System.out.println(">>> 메뉴 조회 실패: ID [" + trimmedMenuId + "]");
                            return new RuntimeException("메뉴를 찾을 수 없습니다: " + trimmedMenuId);
                        });

        String rawId = revenueRepository.getNextRevenueId();
        String finalId = (rawId != null) ? rawId.trim() : null;

        System.out.println(">>> 생성된 Revenue ID: [" + finalId + "]");

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

    public void checkTableNames() {
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});
            while (tables.next()) {
                System.out.println("발견된 테이블: " + tables.getString("TABLE_NAME"));
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public List<SalesRecordResponse> getSalesByDate(String startDate, String endDate){
        System.out.println("DB 호출 직전");
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        List<SalesRecord> result = salesRecordRepository.findByDateWith(start, end);
        System.out.println("DB 결과 size = " + result.size());
        System.out.println(salesRecordRepository.findAll().size());
        return result
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
                .orElseThrow(() -> new RuntimeException("해당 판매 기록이 없습니다."));
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

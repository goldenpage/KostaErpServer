package com.oopsw.kostaerpserver.repository;

import com.oopsw.kostaerpserver.vo.Disposal;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
public class DisposalDAOTest {
    private static final String B_ID = "0000000000";
    private static final LocalDate START_DATE = LocalDate.of(2026, 4, 1);
    private static final LocalDate END_DATE = LocalDate.of(2026, 5, 31);

    @Autowired
    private DisposalDAO disposalDAO;

    @Test
    //폐기 품목 조회 테스트
    void getDisposals() {
        log.info("getDisposals 시작");
        List<Disposal> result = disposalDAO.getDisposals();

        log.info("DB에서 조회된 전체 폐기 품목 수: {}", result != null ? result.size() : "null");
        assertThat(result).isNotNull();
        log.info("getDisposals 성공 & 종료");
    }

    @Test
    //폐기 식자재 이름 조회 테스트
    void getFoodMaterialNames() {
        log.info("getFoodMaterialNames 시작");
        List<String> result = disposalDAO.getFoodMaterialNames();

        log.info("조회된 식자재 이름 목록: {}", result);
        assertThat(result).isNotNull();
        log.info("getFoodMaterialNames 성공 & 종료");
    }

    @Test
    //폐기 식자재 카테고리 조회 테스트
    void getCategories() {
        log.info("getCategories 시작");
        List<String> result = disposalDAO.getCategories();

        log.info("조회된 카테고리 목록: {}", result);
        assertThat(result).isNotNull();
        log.info("getCategories 성공 & 종료");
    }

    @Test
    //사업장 ID 기준 폐기 목록 페이징 조회 테스트
    void getDisposalsFilteredPaging() {
        log.info("getDisposalsFilteredPaging - B_ID: {} 시작", B_ID);
        List<Disposal> result = disposalDAO.getDisposalsFilteredPaging(B_ID, 0, 6);

        log.info("필터 페이징 조회 결과 개수 (Max 6): {}", result != null ? result.size() : "null");
        assertThat(result).isNotNull();
        assertThat(result.size()).isLessThanOrEqualTo(6);
        log.info("getDisposalsFilteredPaging 성공 & 종료");
    }

    @Test
    //특정 사업장의 폐기 데이터 수 조회 테스트
    void getDisposalCount() {
        log.info("getDisposalCount - B_ID: {} 시작", B_ID);
        int result = disposalDAO.getDisposalCount(B_ID);

        log.info("B_ID [{}]의 폐기 데이터 수: {}", B_ID, result);
        assertThat(result).isGreaterThanOrEqualTo(0);
        log.info("getDisposalCount 성공 & 종료");
    }

    @Test
    //특정 사업장의 전체 폐기 데이터 수 조회 테스트
    void getTotalCount() {
        log.info("getTotalCount - B_ID: {} 시작", B_ID);
        int result = disposalDAO.getTotalCount(B_ID);

        log.info("B_ID [{}]의 전체 데이터 수: {}", B_ID, result);
        assertThat(result).isGreaterThanOrEqualTo(0);
        log.info("getTotalCount 성공 & 종료");
    }

    @Test
    //폐기 사유 조회 테스트
    void getReasons() {
        log.info("getReasons 시작");
        List<String> result = disposalDAO.getReasons();

        log.info("조회된 폐기 사유 종류: {}", result);
        assertThat(result).isNotNull();
        log.info("getReasons 성공 & 종료");
    }

    @Test
    //카테고리와 사업장 ID 기준 폐기 목록 조회 테스트
    void getDisposalsByCategoryAndBId() {
        log.info("getDisposalsByCategoryAndBId 시작 - Category: 채소, B_ID: {}", B_ID);
        List<Disposal> result = disposalDAO.getDisposalsByCategoryAndBId("채소", B_ID);

        log.info("채소 카테고리 조회 결과 개수: {}", result != null ? result.size() : "null");
        assertThat(result).isNotNull();
        log.info("getDisposalsByCategoryAndBId 성공 & 종료");
    }

    @Test
    //특정 사업장의 폐기 목록 페이징 테스트
    void getDisposalsPaging() {
        log.info("getDisposalsPaging 시작 - B_ID: {}", B_ID);
        List<Disposal> result = disposalDAO.getDisposalsPaging(B_ID, 0, 6);

        log.info("기본 페이징 조회 결과 개수 (Max 6): {}", result != null ? result.size() : "null");
        assertThat(result).isNotNull();
        assertThat(result.size()).isLessThanOrEqualTo(6);
        log.info("getDisposalsPaging 성공 & 종료");
    }

    @Test
    //폐기 사유 수정 테스트
    void updateReason() {
        String targetId = "DIS001";
        String newReason = "D";
        log.info("updateReason 시작 - Target: {}, Reason: {}", targetId, newReason);

        int result = disposalDAO.updateReason(targetId, newReason);

        log.info("수정된 행(Row)의 개수 (0 또는 1): {}", result);
        assertThat(result).isBetween(0, 1);
        log.info("updateReason 성공 & 종료");
    }

    @Test
    //유통기한이 지난 폐기데이터 조회 테스트
    void getExpiredDisposalIds() {
        log.info("getExpiredDisposalIds 시작 - B_ID: {}", B_ID);
        List<String> result = disposalDAO.getExpiredDisposalIds(B_ID);

        log.info("유통기한 만료된 폐기 ID 목록: {}", result);
        assertThat(result).isNotNull();
        log.info("getExpiredDisposalIds 성공 & 종료");
    }

    @Test
    //폐기율 조회 테스트
    void getDisposalRate() {
        log.info("getDisposalRate 시작 - 기간: {} ~ {}", START_DATE, END_DATE);
        double result = disposalDAO.getDisposalRate(B_ID, START_DATE, END_DATE);

        log.info("조회된 폐기율: {}%", result);
        assertThat(result).isGreaterThanOrEqualTo(0);
        log.info("getDisposalRate 성공 & 종료");
    }

    @Test
    //총 폐기 금액 테스트
    void getTotalDisposalPrice() {
        log.info("getTotalDisposalPrice 시작 - 기간: {} ~ {}", START_DATE, END_DATE);
        int result = disposalDAO.getTotalDisposalPrice(B_ID, START_DATE, END_DATE);

        log.info("조회된 총 폐기 금액: {}원", result);
        assertThat(result).isGreaterThanOrEqualTo(0);
        log.info("getTotalDisposalPrice 성공 & 종료");
    }

    @Test
    //폐기 금액 기준 상위 3개 조회 테스트
    void getTop3DisposalItems() {
        log.info("getTop3DisposalItems 시작 - 기간: {} ~ {}", START_DATE, END_DATE);
        List<Disposal> result = disposalDAO.getTop3DisposalItems(B_ID, START_DATE, END_DATE);

        log.info("상위 3개 품목 조회 개수: {}", result != null ? result.size() : "null");
        assertThat(result).isNotNull();
        assertThat(result.size()).isLessThanOrEqualTo(3);
        log.info("getTop3DisposalItems 성공 & 종료");
    }

    @Test
    //폐기 사유별 비율 조회 테스트
    void getDisposalReasonRatio() {
        log.info("getDisposalReasonRatio 시작 - 기간: {} ~ {}", START_DATE, END_DATE);
        List<Disposal> result = disposalDAO.getDisposalReasonRatio(B_ID, START_DATE, END_DATE);

        log.info("사유별 비율 통계 데이터 개수: {}", result != null ? result.size() : "null");
        assertThat(result).isNotNull();
        log.info("getDisposalReasonRatio 성공 & 종료");
    }

    @Test
    //일별 폐기 수령 및 금액 조회 테스트
    void selectDailyDisposalAmount() {
        log.info("selectDailyDisposalAmount 시작 - 기간: {} ~ {}", START_DATE, END_DATE);
        List<Disposal> result = disposalDAO.selectDailyDisposalAmount(B_ID, START_DATE, END_DATE);

        log.info("일별 폐기 수량/금액 데이터 개수: {}", result != null ? result.size() : "null");
        assertThat(result).isNotNull();
        log.info("selectDailyDisposalAmount 성공 & 종료");
    }

    @Test
    //식자재 타입별 일별 폐기 통계 조회 테스트
    void selectDailyDisposalByType() {
        log.info("selectDailyDisposalByType 시작 - 기간: {} ~ {}", START_DATE, END_DATE);
        List<Disposal> result = disposalDAO.selectDailyDisposalByType(B_ID, START_DATE, END_DATE);

        log.info("식자재 타입별 통계 데이터 개수: {}", result != null ? result.size() : "null");
        assertThat(result).isNotNull();
        log.info("selectDailyDisposalByType 성공 & 종료");
    }
}

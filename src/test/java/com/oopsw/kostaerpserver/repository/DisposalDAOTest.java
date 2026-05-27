package com.oopsw.kostaerpserver.repository;

import com.oopsw.kostaerpserver.vo.DisposalVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class DisposalDAOTest {
    private static final String B_ID = "0000000000";
    private static final LocalDate START_DATE = LocalDate.of(2026, 4, 1);
    private static final LocalDate END_DATE = LocalDate.of(2026, 5, 31);

    @Autowired
    private DisposalDAO disposalDAO;

    @Test
    //폐기 품목 조회 테스트
    void getDisposals(){
        List<DisposalVO> result = disposalDAO.getDisposals();
        assertThat(result).isNotNull();
    }

    @Test
    //폐기 식자재 이름 조회 테스트
    void getFoodMaterialNames() {
        List<String> result = disposalDAO.getFoodMaterialNames();
        assertThat(result).isNotNull();
    }

    @Test
    //폐기 식자재 카테고리 조회 테스트
    void getCategories() {
        List<String> result = disposalDAO.getCategories();
        assertThat(result).isNotNull();
    }

    @Test
    //사업장 ID 기준 폐기 목록 페이징 조회 테스트
    void getDisposalsFilteredPaging() {
        List<DisposalVO> result = disposalDAO.getDisposalsFilteredPaging(B_ID, 0, 6);
        assertThat(result).isNotNull();
        assertThat(result.size()).isLessThanOrEqualTo(6);
    }

    @Test
    //특정 사업장의 폐기 데이터 수 조회 테스트
    void getDisposalCount() {
        int result = disposalDAO.getDisposalCount(B_ID);
        assertThat(result).isGreaterThanOrEqualTo(0);
    }

    @Test
    //특정 사업장의 전체 폐기 데이터 수 조회 테스트
    void getTotalCount() {
        int result = disposalDAO.getTotalCount(B_ID);
        assertThat(result).isGreaterThanOrEqualTo(0);
    }

    @Test
    //폐기 사유 조회 테스트
    void getReasons() {
        List<String> result = disposalDAO.getReasons();
        assertThat(result).isNotNull();
    }

    @Test
    //카테고리와 사업장 ID 기준 폐기 목록 조회 테스트
    void getDisposalsByCategoryAndBId() {
        List<DisposalVO> result = disposalDAO.getDisposalsByCategoryAndBId("채소", B_ID);
        assertThat(result).isNotNull();
    }

    @Test
    //특정 사업장의 폐기 목록 페이징 테스트
    void getDisposalsPaging() {
        List<DisposalVO> result = disposalDAO.getDisposalsPaging(B_ID, 0, 6);
        assertThat(result).isNotNull();
        assertThat(result.size()).isLessThanOrEqualTo(6);
    }

    @Test
    //폐기 사유 수정 테스트
    void updateReason() {
        int result = disposalDAO.updateReason("DIS001", "D");
        assertThat(result).isBetween(0, 1);
    }

    @Test
    //유통기한이 지난 폐기데이터 조회 테스트
    void getExpiredDisposalIds() {
        List<String> result = disposalDAO.getExpiredDisposalIds(B_ID);
        assertThat(result).isNotNull();
    }

    @Test
    //폐기율 조회 테스트
    void getDisposalRate() {
        double result = disposalDAO.getDisposalRate(B_ID, START_DATE, END_DATE);
        assertThat(result).isGreaterThanOrEqualTo(0);
    }

    @Test
    //총 폐기 금액 테스트
    void getTotalDisposalPrice() {
        int result = disposalDAO.getTotalDisposalPrice(B_ID, START_DATE, END_DATE);
        assertThat(result).isGreaterThanOrEqualTo(0);
    }

    @Test
    //폐기 금액 기준 상위 3개 조회 테스트
    void getTop3DisposalItems() {
        List<DisposalVO> result = disposalDAO.getTop3DisposalItems(B_ID, START_DATE, END_DATE);
        assertThat(result).isNotNull();
        assertThat(result.size()).isLessThanOrEqualTo(3);
    }

    @Test
    //폐기 사유별 비율 조회 테스트
    void getDisposalReasonRatio() {
        List<DisposalVO> result = disposalDAO.getDisposalReasonRatio(B_ID, START_DATE, END_DATE);
        assertThat(result).isNotNull();
    }

    @Test
    //일별 폐기 수령 및 금액 조회 테스트
    void selectDailyDisposalAmount() {
        List<DisposalVO> result = disposalDAO.selectDailyDisposalAmount(B_ID, START_DATE, END_DATE);
        assertThat(result).isNotNull();
    }

    @Test
    //식자재 타입별 일별 폐기 통계 조회 테스트
    void selectDailyDisposalByType() {
        List<DisposalVO> result = disposalDAO.selectDailyDisposalByType(B_ID, START_DATE, END_DATE);
        assertThat(result).isNotNull();
    }
}

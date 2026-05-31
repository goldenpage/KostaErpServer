package com.oopsw.kostaerpserver.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.oopsw.kostaerpserver.service.Interface.DisposalService;
import java.time.LocalDate;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.oopsw.kostaerpserver.repository.DisposalDAO;
import com.oopsw.kostaerpserver.vo.Disposal;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
public class DisposalServiceTest {
    private static final String B_ID = "0000000000";
    private static final LocalDate START_DATE = LocalDate.of(2026, 4, 1);
    private static final LocalDate END_DATE = LocalDate.of(2026, 5, 31);

    @MockitoBean
    private DisposalDAO disposalDAO;

    @Autowired
    private DisposalService disposalService;

    @Test
    //Service가 DAO의 전체 폐기 목록 조회 기능을 정상 호출하는지 테스트
    void getDisposalsTest() {
        log.info("getDisposalsTest 시작");
        List<Disposal> disposals = List.of(new Disposal());
        when(disposalDAO.getDisposals()).thenReturn(disposals);

        List<Disposal> result = disposalService.getDisposals();

        log.info("Expected disposals size: {}, Result size: {}", disposals.size(), result.size());
        assertThat(result).isSameAs(disposals);
        verify(disposalDAO).getDisposals();
        log.info("getDisposalsTest 성공 & 종료");
    }

    @Test
    //식자재 이름 목록 조회 기능 테스트
    void getFoodMaterialNamesTest() {
        log.info("getFoodMaterialNamesTest 시작");
        List<String> names = List.of("양파", "감자");
        when(disposalDAO.getFoodMaterialNames()).thenReturn(names);

        List<String> result = disposalService.getFoodMaterialNames();

        log.info("Fetched FoodMaterialNames: {}", result);
        assertThat(result).containsExactly("양파", "감자");
        verify(disposalDAO).getFoodMaterialNames();
        log.info("getFoodMaterialNamesTest 성공 & 종료");
    }

    @Test
    //카테고리 목록 조회 기능 테스트
    void getCategoriesTest() {
        log.info("getCategoriesTest 시작");
        List<String> categories = List.of("채소", "육류");
        when(disposalDAO.getCategories()).thenReturn(categories);

        List<String> result = disposalService.getCategories();

        log.info("Fetched Categories: {}", result);
        assertThat(result).containsExactly("채소", "육류");
        verify(disposalDAO).getCategories();
        log.info("getCategoriesTest 성공 & 종료");
    }

    @Test
    //페이지 번호를 offset으로 변환하는지 테스트
    void getDisposalsFilteredPaging_convertsPageToOffsetTest() {
        log.info("getDisposalsFilteredPaging_convertsPageToOffsetTest 시작");
        List<Disposal> disposals = List.of(new Disposal());
        when(disposalDAO.getDisposalsFilteredPaging(B_ID, 6, 6)).thenReturn(disposals);

        List<Disposal> result = disposalService.getDisposalsFilteredPaging(B_ID, 2, 6);

        log.info("Paging result count: {}", result.size());
        assertThat(result).isSameAs(disposals);
        verify(disposalDAO).getDisposalsFilteredPaging(B_ID, 6, 6);
        log.info("getDisposalsFilteredPaging_convertsPageToOffsetTest 성공 & 종료");
    }

    @Test
    //page가 1보다 작을 경우 offset을 0으로 처리하는지 테스트
    void getDisposalsFilteredPaging_ZeroOffsetTest() {
        log.info("getDisposalsFilteredPaging_ZeroOffsetTest 시작");
        when(disposalDAO.getDisposalsFilteredPaging(B_ID, 0, 6)).thenReturn(List.of());

        List<Disposal> result = disposalService.getDisposalsFilteredPaging(B_ID, 0, 6);

        log.info("Zero offset result isEmpty: {}", result.isEmpty());
        assertThat(result).isEmpty();
        verify(disposalDAO).getDisposalsFilteredPaging(B_ID, 0, 6);
        log.info("getDisposalsFilteredPaging_ZeroOffsetTest 성공 & 종료");
    }

    @Test
    //폐기 데이터 개수 조회 기능 테스트
    void getDisposalCountTest() {
        log.info("getDisposalCountTest 시작");
        when(disposalDAO.getDisposalCount(B_ID)).thenReturn(3);

        int result = disposalService.getDisposalCount(B_ID);

        log.info("Disposal count for B_ID {}: {}", B_ID, result);
        assertThat(result).isEqualTo(3);
        verify(disposalDAO).getDisposalCount(B_ID);
        log.info("getDisposalCountTest 성공 & 종료");
    }

    @Test
    //전체 폐기 데이터 개수 조회 기능 테스트
    void getTotalCountTest() {
        log.info("getTotalCountTest 시작");
        when(disposalDAO.getTotalCount(B_ID)).thenReturn(10);

        int result = disposalService.getTotalCount(B_ID);

        log.info("Total count for B_ID {}: {}", B_ID, result);
        assertThat(result).isEqualTo(10);
        verify(disposalDAO).getTotalCount(B_ID);
        log.info("getTotalCountTest 성공 & 종료");
    }

    @Test
    //폐기 사유 목록 조회 기능 테스트
    void getReasonsTest() {
        log.info("getReasonsTest 시작");
        List<String> reasons = List.of("유통기한 만료", "파손");
        when(disposalDAO.getReasons()).thenReturn(reasons);

        List<String> result = disposalService.getReasons();

        log.info("Fetched Reasons: {}", result);
        assertThat(result).containsExactly("유통기한 만료", "파손");
        verify(disposalDAO).getReasons();
        log.info("getReasonsTest 성공 & 종료");
    }

    @Test
    //카테고리 + 사업장 기준 폐기 목록 조회 기능 테스트
    void getDisposalsByCategoryAndBIdTest() {
        log.info("getDisposalsByCategoryAndBIdTest 시작");
        List<Disposal> disposals = List.of(new Disposal());
        when(disposalDAO.getDisposalsByCategoryAndBId("채소", B_ID)).thenReturn(disposals);

        List<Disposal> result = disposalService.getDisposalsByCategoryAndBId("채소", B_ID);

        log.info("Category: 채소, B_ID: {}, Result count: {}", B_ID, result.size());
        assertThat(result).isSameAs(disposals);
        verify(disposalDAO).getDisposalsByCategoryAndBId("채소", B_ID);
        log.info("getDisposalsByCategoryAndBIdTest 성공 & 종료");
    }

    @Test
    //페이지 번호를 offset으로 변환하는 페이징 기능 테스트
    void getDisposalsPaging_convertsPageToOffsetTest() {
        log.info("getDisposalsPaging_convertsPageToOffsetTest 시작");
        List<Disposal> disposals = List.of(new Disposal());
        when(disposalDAO.getDisposalsPaging(B_ID, 12, 6)).thenReturn(disposals);

        List<Disposal> result = disposalService.getDisposalsPaging(B_ID, 3, 6);

        log.info("Paging result count: {}", result.size());
        assertThat(result).isSameAs(disposals);
        verify(disposalDAO).getDisposalsPaging(B_ID, 12, 6);
        log.info("getDisposalsPaging_convertsPageToOffsetTest 성공 & 종료");
    }

    @Test
    //폐기 사유 수정 성공 테스트
    void updateReason_successTest() {
        log.info("updateReason_successTest 시작");
        when(disposalDAO.updateReason("DIS001", "D")).thenReturn(1);

        boolean result = disposalService.updateReason("DIS001", "D");

        log.info("Update success result: {}", result);
        assertThat(result).isTrue();
        verify(disposalDAO).updateReason("DIS001", "D");
        log.info("updateReason_successTest 성공 & 종료");
    }

    @Test
    //폐기 사유 수정 실패 테스트
    void updateReason_failTest() {
        log.info("updateReason_failTest 시작");
        when(disposalDAO.updateReason("DIS999", "D")).thenReturn(0);

        boolean result = disposalService.updateReason("DIS999", "D");

        log.info("Update fail result (Expected false): {}", result);
        assertThat(result).isFalse();
        verify(disposalDAO).updateReason("DIS999", "D");
        log.info("updateReason_failTest 성공 & 종료");
    }

    @Test
    //유통기한 지난 폐기 ID 조회 기능 테스트
    void getExpiredDisposalIdsTest() {
        log.info("getExpiredDisposalIdsTest 시작");
        List<String> ids = List.of("DIS001");
        when(disposalDAO.getExpiredDisposalIds(B_ID)).thenReturn(ids);

        List<String> result = disposalService.getExpiredDisposalIds(B_ID);

        log.info("Expired IDs for B_ID {}: {}", B_ID, result);
        assertThat(result).containsExactly("DIS001");
        verify(disposalDAO).getExpiredDisposalIds(B_ID);
        log.info("getExpiredDisposalIdsTest 성공 & 종료");
    }

    @Test
    //폐기율 조회 기능 테스트
    void getDisposalRateTest() {
        log.info("getDisposalRateTest 시작");
        when(disposalDAO.getDisposalRate(B_ID, START_DATE, END_DATE)).thenReturn(12.5);

        double result = disposalService.getDisposalRate(B_ID, START_DATE, END_DATE);

        log.info("Disposal Rate between {} and {}: {}%", START_DATE, END_DATE, result);
        assertThat(result).isEqualTo(12.5);
        verify(disposalDAO).getDisposalRate(B_ID, START_DATE, END_DATE);
        log.info("getDisposalRateTest 성공 & 종료");
    }

    @Test
    //총 폐기 금액 조회 기능 테스트
    void getTotalDisposalPriceTest() {
        log.info("getTotalDisposalPriceTest 시작");
        when(disposalDAO.getTotalDisposalPrice(B_ID, START_DATE, END_DATE)).thenReturn(50000);

        int result = disposalService.getTotalDisposalPrice(B_ID, START_DATE, END_DATE);

        log.info("Total Price between {} and {}: {}원", START_DATE, END_DATE, result);
        assertThat(result).isEqualTo(50000);
        verify(disposalDAO).getTotalDisposalPrice(B_ID, START_DATE, END_DATE);
        log.info("getTotalDisposalPriceTest 성공 & 종료");
    }

    @Test
    //폐기 금액 상위 3개 품목 조회 기능 테스트
    void getTop3DisposalItemsTest() {
        log.info("getTop3DisposalItemsTest 시작");
        List<Disposal> disposals = List.of(new Disposal());
        when(disposalDAO.getTop3DisposalItems(B_ID, START_DATE, END_DATE)).thenReturn(disposals);

        List<Disposal> result = disposalService.getTop3DisposalItems(B_ID, START_DATE, END_DATE);

        log.info("Top 3 items: {}", result);
        assertThat(result).isSameAs(disposals);
        verify(disposalDAO).getTop3DisposalItems(B_ID, START_DATE, END_DATE);
        log.info("getTop3DisposalItemsTest 성공 & 종료");
    }

    @Test
    //폐기 사유 비율 조회 기능 테스트
    void getDisposalReasonRatioTest() {
        log.info("getDisposalReasonRatioTest 시작");
        List<Disposal> disposals = List.of(new Disposal());
        when(disposalDAO.getDisposalReasonRatio(B_ID, START_DATE, END_DATE)).thenReturn(disposals);

        List<Disposal> result = disposalService.getDisposalReasonRatio(B_ID, START_DATE, END_DATE);

        log.info("Reason ratio results size: {}", result.size());
        assertThat(result).isSameAs(disposals);
        verify(disposalDAO).getDisposalReasonRatio(B_ID, START_DATE, END_DATE);
        log.info("getDisposalReasonRatioTest 성공 & 종료");
    }

    @Test
    //일별 폐기 수량 및 금액 조회 기능 테스트
    void selectDailyDisposalAmountTest() {
        log.info("selectDailyDisposalAmountTest 시작");
        List<Disposal> disposals = List.of(new Disposal());
        when(disposalDAO.selectDailyDisposalAmount(B_ID, START_DATE, END_DATE)).thenReturn(disposals);

        List<Disposal> result = disposalService.selectDailyDisposalAmount(B_ID, START_DATE, END_DATE);

        log.info("Daily disposal amount size: {}", result.size());
        assertThat(result).isSameAs(disposals);
        verify(disposalDAO).selectDailyDisposalAmount(B_ID, START_DATE, END_DATE);
        log.info("selectDailyDisposalAmountTest 성공 & 종료");
    }

    @Test
    //식자재 타입별 일별 폐기 통계 조회 기능 테스트
    void selectDailyDisposalByTypeTest() {
        log.info("selectDailyDisposalByTypeTest 시작");
        List<Disposal> disposals = List.of(new Disposal());
        when(disposalDAO.selectDailyDisposalByType(B_ID, START_DATE, END_DATE)).thenReturn(disposals);

        List<Disposal> result = disposalService.selectDailyDisposalByType(B_ID, START_DATE, END_DATE);

        log.info("Daily disposal by type size: {}", result.size());
        assertThat(result).isSameAs(disposals);
        verify(disposalDAO).selectDailyDisposalByType(B_ID, START_DATE, END_DATE);
        log.info("selectDailyDisposalByTypeTest 성공 & 종료");
    }
}

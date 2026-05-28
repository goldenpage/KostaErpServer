package com.oopsw.kostaerpserver.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.oopsw.kostaerpserver.repository.DisposalDAO;
import com.oopsw.kostaerpserver.vo.Disposal;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

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
        List<Disposal> disposals = List.of(new Disposal());
        when(disposalDAO.getDisposals()).thenReturn(disposals);
        List<Disposal> result = disposalService.getDisposals();
        assertThat(result).isSameAs(disposals);
        verify(disposalDAO).getDisposals();
    }

    @Test
    //식자재 이름 목록 조회 기능 테스트
    void getFoodMaterialNamesTest() {
        List<String> names = List.of("양파", "감자");
        when(disposalDAO.getFoodMaterialNames()).thenReturn(names);
        List<String> result = disposalService.getFoodMaterialNames();
        assertThat(result).containsExactly("양파", "감자");
        verify(disposalDAO).getFoodMaterialNames();
    }

    @Test
    //카테고리 목록 조회 기능 테스트
    void getCategoriesTest() {
        List<String> categories = List.of("채소", "육류");
        when(disposalDAO.getCategories()).thenReturn(categories);
        List<String> result = disposalService.getCategories();
        assertThat(result).containsExactly("채소", "육류");
        verify(disposalDAO).getCategories();
    }

    @Test
    //페이지 번호를 offset으로 변환하는지 테스트
    void getDisposalsFilteredPaging_convertsPageToOffsetTest() {
        List<Disposal> disposals = List.of(new Disposal());
        when(disposalDAO.getDisposalsFilteredPaging(B_ID, 6, 6)).thenReturn(disposals);
        List<Disposal> result = disposalService.getDisposalsFilteredPaging(B_ID, 2, 6);
        assertThat(result).isSameAs(disposals);
        verify(disposalDAO).getDisposalsFilteredPaging(B_ID, 6, 6);
    }

    @Test
    //page가 1보다 작을 경우 offset을 0으로 처리하는지 테스트
    void getDisposalsFilteredPaging_ZeroOffsetTest() {
        when(disposalDAO.getDisposalsFilteredPaging(B_ID, 0, 6)).thenReturn(List.of());
        List<Disposal> result = disposalService.getDisposalsFilteredPaging(B_ID, 0, 6);
        assertThat(result).isEmpty();
        verify(disposalDAO).getDisposalsFilteredPaging(B_ID, 0, 6);
    }

    @Test
    //폐기 데이터 개수 조회 기능 테스트
    void getDisposalCountTest() {
        when(disposalDAO.getDisposalCount(B_ID)).thenReturn(3);
        int result = disposalService.getDisposalCount(B_ID);
        assertThat(result).isEqualTo(3);
        verify(disposalDAO).getDisposalCount(B_ID);
    }

    @Test
    //전체 폐기 데이터 개수 조회 기능 테스트
    void getTotalCountTest() {
        when(disposalDAO.getTotalCount(B_ID)).thenReturn(10);
        int result = disposalService.getTotalCount(B_ID);
        assertThat(result).isEqualTo(10);
        verify(disposalDAO).getTotalCount(B_ID);
    }

    @Test
    //폐기 사유 목록 조회 기능 테스트
    void getReasonsTest() {
        List<String> reasons = List.of("유통기한 만료", "파손");
        when(disposalDAO.getReasons()).thenReturn(reasons);
        List<String> result = disposalService.getReasons();
        assertThat(result).containsExactly("유통기한 만료", "파손");
        verify(disposalDAO).getReasons();
    }

    @Test
    //카테고리 + 사업장 기준 폐기 목록 조회 기능 테스트
    void getDisposalsByCategoryAndBIdTest() {
        List<Disposal> disposals = List.of(new Disposal());
        when(disposalDAO.getDisposalsByCategoryAndBId("채소", B_ID)).thenReturn(disposals);
        List<Disposal> result = disposalService.getDisposalsByCategoryAndBId("채소", B_ID);
        assertThat(result).isSameAs(disposals);
        verify(disposalDAO).getDisposalsByCategoryAndBId("채소", B_ID);
    }

    @Test
    //페이지 번호를 offset으로 변환하는 페이징 기능 테스트
    void getDisposalsPaging_convertsPageToOffsetTest() {
        List<Disposal> disposals = List.of(new Disposal());
        when(disposalDAO.getDisposalsPaging(B_ID, 12, 6)).thenReturn(disposals);
        List<Disposal> result =disposalService.getDisposalsPaging(B_ID, 3, 6);
        assertThat(result).isSameAs(disposals);
        verify(disposalDAO).getDisposalsPaging(B_ID, 12, 6);
    }

    @Test
    //폐기 사유 수정 성공 테스트
    void updateReason_successTest() {
        when(disposalDAO.updateReason("DIS001", "D")).thenReturn(1);
        boolean result = disposalService.updateReason("DIS001", "D");
        assertThat(result).isTrue();
        verify(disposalDAO).updateReason("DIS001", "D");
    }

    @Test
    //폐기 사유 수정 실패 테스트
    void updateReason_failTest() {
        when(disposalDAO.updateReason("DIS999", "D")).thenReturn(0);
        boolean result = disposalService.updateReason("DIS999", "D");
        assertThat(result).isFalse();
        verify(disposalDAO).updateReason("DIS999", "D");
    }

    @Test
    //유통기한 지난 폐기 ID 조회 기능 테스트
    void getExpiredDisposalIdsTest() {
        List<String> ids = List.of("DIS001");
        when(disposalDAO.getExpiredDisposalIds(B_ID)).thenReturn(ids);
        List<String> result = disposalService.getExpiredDisposalIds(B_ID);
        assertThat(result).containsExactly("DIS001");
        verify(disposalDAO).getExpiredDisposalIds(B_ID);
    }

    @Test
    //폐기율 조회 기능 테스트
    void getDisposalRateTest() {
        when(disposalDAO.getDisposalRate(B_ID, START_DATE, END_DATE)).thenReturn(12.5);
        double result = disposalService.getDisposalRate(B_ID, START_DATE, END_DATE);
        assertThat(result).isEqualTo(12.5);
        verify(disposalDAO).getDisposalRate(B_ID, START_DATE, END_DATE);
    }

    @Test
    //총 폐기 금액 조회 기능 테스트
    void getTotalDisposalPriceTest() {
        when(disposalDAO.getTotalDisposalPrice(B_ID, START_DATE, END_DATE)).thenReturn(50000);
        int result = disposalService.getTotalDisposalPrice(B_ID, START_DATE, END_DATE);
        assertThat(result).isEqualTo(50000);
        verify(disposalDAO).getTotalDisposalPrice(B_ID, START_DATE, END_DATE);
    }

    @Test
    //폐기 금액 상위 3개 품목 조회 기능 테스트
    void getTop3DisposalItemsTest() {
        List<Disposal> disposals = List.of(new Disposal());
        when(disposalDAO.getTop3DisposalItems(B_ID, START_DATE, END_DATE)).thenReturn(disposals);
        List<Disposal> result = disposalService.getTop3DisposalItems(B_ID, START_DATE, END_DATE);
        assertThat(result).isSameAs(disposals);
        verify(disposalDAO).getTop3DisposalItems(B_ID, START_DATE, END_DATE);
    }

    @Test
    //폐기 사유 비율 조회 기능 테스트
    void getDisposalReasonRatioTest() {
        List<Disposal> disposals = List.of(new Disposal());
        when(disposalDAO.getDisposalReasonRatio(B_ID, START_DATE, END_DATE)).thenReturn(disposals);
        List<Disposal> result = disposalService.getDisposalReasonRatio(B_ID, START_DATE, END_DATE);
        assertThat(result).isSameAs(disposals);
        verify(disposalDAO).getDisposalReasonRatio(B_ID, START_DATE, END_DATE);
    }

    @Test
    //일별 폐기 수량 및 금액 조회 기능 테스트
    void selectDailyDisposalAmountTest() {
        List<Disposal> disposals = List.of(new Disposal());
        when(disposalDAO.selectDailyDisposalAmount(B_ID, START_DATE, END_DATE)).thenReturn(disposals);
        List<Disposal> result = disposalService.selectDailyDisposalAmount(B_ID, START_DATE, END_DATE);
        assertThat(result).isSameAs(disposals);
        verify(disposalDAO).selectDailyDisposalAmount(B_ID, START_DATE, END_DATE);
    }

    @Test
    //식자재 타입별 일별 폐기 통계 조회 기능 테스트
    void selectDailyDisposalByTypeTest() {
        List<Disposal> disposals = List.of(new Disposal());
        when(disposalDAO.selectDailyDisposalByType(B_ID, START_DATE, END_DATE)).thenReturn(disposals);
        List<Disposal> result = disposalService.selectDailyDisposalByType(B_ID, START_DATE, END_DATE);
        assertThat(result).isSameAs(disposals);
        verify(disposalDAO).selectDailyDisposalByType(B_ID, START_DATE, END_DATE);
    }
}

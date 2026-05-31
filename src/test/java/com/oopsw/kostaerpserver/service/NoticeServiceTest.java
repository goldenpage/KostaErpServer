package com.oopsw.kostaerpserver.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.oopsw.kostaerpserver.service.Interface.NoticeService;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.oopsw.kostaerpserver.repository.NoticeDAO;
import com.oopsw.kostaerpserver.vo.Notice;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
public class NoticeServiceTest {
    private static final String B_ID = "0000000000";

    @MockitoBean
    private NoticeDAO noticeDAO;

    @Autowired
    private NoticeService noticeService;

    @Test
    //폐기 알림 생성 서비스 테스트
    void insertNotice_returnsTrueWhenOneRowInserted() {
        String targetId = "DIS011";
        log.info("insertNotice_returnsTrueWhenOneRowInserted - Target: {}", targetId);
        when(noticeDAO.insertNotice(targetId)).thenReturn(1);

        boolean result = noticeService.insertNotice(targetId);

        log.info("Mock DAO 반환값: 1 -> 서비스 최종 반환값: {}", result);
        assertThat(result).isTrue();
        verify(noticeDAO).insertNotice(targetId);
        log.info("insertNotice_returnsTrueWhenOneRowInserted 성공 & 종료");
    }

    @Test
    //폐기 알림 생성 실패 테스트
    void insertNotice_returnsFalseWhenNoRowInserted() {
        String targetId = "DIS999";
        log.info("insertNotice_returnsFalseWhenNoRowInserted - Target: {}", targetId);
        when(noticeDAO.insertNotice(targetId)).thenReturn(0);

        boolean result = noticeService.insertNotice(targetId);

        log.info("Mock DAO 반환값: 0 -> 서비스 최종 반환값: {}", result);
        assertThat(result).isFalse();
        verify(noticeDAO).insertNotice(targetId);
        log.info("insertNotice_returnsFalseWhenNoRowInserted 성공 & 종료");
    }

    @Test
    //알림 목록 조회 서비스 테스트
    void getNoticeList() {
        log.info("getNoticeList - B_ID: {}", B_ID);
        List<Notice> notices = List.of(new Notice());
        when(noticeDAO.getNoticeList(B_ID)).thenReturn(notices);

        List<Notice> result = noticeService.getNoticeList(B_ID);

        log.info("조회된 알림 리스트 객체 동일 여부: {}, 개수: {}", (result == notices), result.size());
        assertThat(result).isSameAs(notices);
        verify(noticeDAO).getNoticeList(B_ID);
        log.info("getNoticeList 성공 & 종료");
    }

    @Test
    //전체 알림 삭제 서비스 테스트
    void deleteNoticeAll_returnsDeletedRowCount() {
        log.info("deleteNoticeAll_returnsDeletedRowCount");
        when(noticeDAO.deleteNoticeAll()).thenReturn(3);

        int result = noticeService.deleteNoticeAll();

        log.info("삭제된 알림 데이터 수: {}건", result);
        assertThat(result).isEqualTo(3);
        verify(noticeDAO).deleteNoticeAll();
        log.info("deleteNoticeAll_returnsDeletedRowCount 성공 & 종료");
    }

    @Test
    //알림 읽음 처리 성공 테스트
    void updateReadYn_returnsTrueWhenOneRowUpdated() {
        String targetNoticeId = "N001";
        log.info("updateReadYn_returnsTrueWhenOneRowUpdated - NoticeID: {}", targetNoticeId);
        when(noticeDAO.updateReadYn(targetNoticeId)).thenReturn(1);

        boolean result = noticeService.updateReadYn(targetNoticeId);

        log.info("Mock DAO 반환값: 1 -> 서비스 읽음 처리 반환값: {}", result);
        assertThat(result).isTrue();
        verify(noticeDAO).updateReadYn(targetNoticeId);
        log.info("updateReadYn_returnsTrueWhenOneRowUpdated 성공 & 종료");
    }

    @Test
    //알림 읽음 처리 실패 테스트
    void updateReadYn_returnsFalseWhenNoRowUpdated() {
        String targetNoticeId = "N999";
        log.info("updateReadYn_returnsFalseWhenNoRowUpdated - NoticeID: {}", targetNoticeId);
        when(noticeDAO.updateReadYn(targetNoticeId)).thenReturn(0);

        boolean result = noticeService.updateReadYn(targetNoticeId);

        log.info("Mock DAO 반환값: 0 -> 서비스 읽음 처리 반환값: {}", result);
        assertThat(result).isFalse();
        verify(noticeDAO).updateReadYn(targetNoticeId);
        log.info("updateReadYn_returnsFalseWhenNoRowUpdated 성공 & 종료");
    }

    @Test
    //사업장별 알림 리스트 조회 테스트
    void getExpiredIdList() {
        log.info("getExpiredIdList - B_ID: {}", B_ID);
        List<String> ids = List.of("DIS011");
        when(noticeDAO.getExpiredIdList(B_ID)).thenReturn(ids);

        List<String> result = noticeService.getExpiredIdList(B_ID);

        log.info("반환된 만료 ID 목록: {}", result);
        assertThat(result).containsExactly("DIS011");
        verify(noticeDAO).getExpiredIdList(B_ID);
        log.info("getExpiredIdList 성공 & 종료");
    }

    @Test
    //알림 개수 조회 테스트
    void getExpiredCount() {
        log.info("getExpiredCount - B_ID: {}", B_ID);
        when(noticeDAO.getExpiredCount(B_ID)).thenReturn(5);

        int result = noticeService.getExpiredCount(B_ID);

        log.info("B_ID [{}]의 만료 건수: {}건", B_ID, result);
        assertThat(result).isEqualTo(5);
        verify(noticeDAO).getExpiredCount(B_ID);
        log.info("getExpiredCount 성공 & 종료");
    }

    @Test
    //고체 폐기물 총합 조회 테스트
    void getSolidTotal() {
        log.info("getSolidTotal - B_ID: {}", B_ID);
        when(noticeDAO.getSolidTotal(B_ID)).thenReturn(7);

        int result = noticeService.getSolidTotal(B_ID);

        log.info("B_ID [{}]의 고체 폐기물 총합: {}", B_ID, result);
        assertThat(result).isEqualTo(7);
        verify(noticeDAO).getSolidTotal(B_ID);
        log.info("getSolidTotal 성공 & 종료");
    }

    @Test
    //액체 폐기물 총합 조회 테스트
    void getLiquidTotal() {
        log.info("getLiquidTotal - B_ID: {}", B_ID);
        when(noticeDAO.getLiquidTotal(B_ID)).thenReturn(4);

        int result = noticeService.getLiquidTotal(B_ID);

        log.info("B_ID [{}]의 액체 폐기물 총합: {}", B_ID, result);
        assertThat(result).isEqualTo(4);
        verify(noticeDAO).getLiquidTotal(B_ID);
        log.info("getLiquidTotal 성공 & 종료");
    }

    @Test
    //최대 경과일 조회 테스트
    void getMaxOverDay() {
        log.info("getMaxOverDay - B_ID: {}", B_ID);
        when(noticeDAO.getMaxOverDay(B_ID)).thenReturn(10);

        int result = noticeService.getMaxOverDay(B_ID);

        log.info("B_ID [{}]의 최대 경과일: {}일", B_ID, result);
        assertThat(result).isEqualTo(10);
        verify(noticeDAO).getMaxOverDay(B_ID);
        log.info("getMaxOverDay 성공 & 종료");
    }
}

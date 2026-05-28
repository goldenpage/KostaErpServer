package com.oopsw.kostaerpserver.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.oopsw.kostaerpserver.repository.NoticeDAO;
import com.oopsw.kostaerpserver.vo.Notice;

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
        when(noticeDAO.insertNotice("DIS011")).thenReturn(1);

        boolean result = noticeService.insertNotice("DIS011");

        assertThat(result).isTrue();
        verify(noticeDAO).insertNotice("DIS011");
    }

    @Test
    //폐기 알림 생성 실패 테스트
    void insertNotice_returnsFalseWhenNoRowInserted() {
        when(noticeDAO.insertNotice("DIS999")).thenReturn(0);

        boolean result = noticeService.insertNotice("DIS999");

        assertThat(result).isFalse();
        verify(noticeDAO).insertNotice("DIS999");
    }

    @Test
    //알림 목록 조회 서비스 테스트
    void getNoticeList() {
        List<Notice> notices = List.of(new Notice());
        when(noticeDAO.getNoticeList(B_ID)).thenReturn(notices);

        List<Notice> result = noticeService.getNoticeList(B_ID);

        assertThat(result).isSameAs(notices);
        verify(noticeDAO).getNoticeList(B_ID);
    }

    @Test
    //전체 알림 삭제 서비스 테스트
    void deleteNoticeAll_returnsDeletedRowCount() {
        when(noticeDAO.deleteNoticeAll()).thenReturn(3);

        int result = noticeService.deleteNoticeAll();

        assertThat(result).isEqualTo(3);
        verify(noticeDAO).deleteNoticeAll();
    }

    @Test
    //알림 읽음 처리 성공 테스트
    void updateReadYn_returnsTrueWhenOneRowUpdated() {
        when(noticeDAO.updateReadYn("N001")).thenReturn(1);

        boolean result = noticeService.updateReadYn("N001");

        assertThat(result).isTrue();
        verify(noticeDAO).updateReadYn("N001");
    }

    @Test
    //알림 읽음 처리 실패 테스트
    void updateReadYn_returnsFalseWhenNoRowUpdated() {
        when(noticeDAO.updateReadYn("N999")).thenReturn(0);

        boolean result = noticeService.updateReadYn("N999");

        assertThat(result).isFalse();
        verify(noticeDAO).updateReadYn("N999");
    }

    @Test
    //사업장별 알림 리스트 조회 테스트
    void getExpiredIdList() {
        List<String> ids = List.of("DIS011");
        when(noticeDAO.getExpiredIdList(B_ID)).thenReturn(ids);

        List<String> result = noticeService.getExpiredIdList(B_ID);

        assertThat(result).containsExactly("DIS011");
        verify(noticeDAO).getExpiredIdList(B_ID);
    }

    @Test
    //알림 개수 조회 테스트
    void getExpiredCount() {
        when(noticeDAO.getExpiredCount(B_ID)).thenReturn(5);

        int result = noticeService.getExpiredCount(B_ID);

        assertThat(result).isEqualTo(5);
        verify(noticeDAO).getExpiredCount(B_ID);
    }

    @Test
    //고체 폐기물 총합 조회 테스트
    void getSolidTotal() {
        when(noticeDAO.getSolidTotal(B_ID)).thenReturn(7);

        int result = noticeService.getSolidTotal(B_ID);

        assertThat(result).isEqualTo(7);
        verify(noticeDAO).getSolidTotal(B_ID);
    }

    @Test
    //액체 폐기물 총합 조회 테스트
    void getLiquidTotal() {
        when(noticeDAO.getLiquidTotal(B_ID)).thenReturn(4);

        int result = noticeService.getLiquidTotal(B_ID);

        assertThat(result).isEqualTo(4);
        verify(noticeDAO).getLiquidTotal(B_ID);
    }

    @Test
    //최대 경과일 조회 테스트
    void getMaxOverDay() {
        when(noticeDAO.getMaxOverDay(B_ID)).thenReturn(10);

        int result = noticeService.getMaxOverDay(B_ID);

        assertThat(result).isEqualTo(10);
        verify(noticeDAO).getMaxOverDay(B_ID);
    }
}

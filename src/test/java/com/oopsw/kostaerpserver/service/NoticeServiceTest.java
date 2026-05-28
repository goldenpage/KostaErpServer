package com.oopsw.kostaerpserver.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.oopsw.kostaerpserver.repository.NoticeDAO;
import com.oopsw.kostaerpserver.vo.NoticeVO;

@SpringBootTest
public class NoticeServiceTest {
    private static final String B_ID = "0000000000";

    @MockitoBean
    private NoticeDAO noticeDAO;

    @Autowired
    private NoticeService noticeService;

    @Test
    void insertNotice_returnsTrueWhenOneRowInserted() {
        when(noticeDAO.insertNotice("DIS011")).thenReturn(1);

        boolean result = noticeService.insertNotice("DIS011");

        assertThat(result).isTrue();
        verify(noticeDAO).insertNotice("DIS011");
    }

    @Test
    void insertNotice_returnsFalseWhenNoRowInserted() {
        when(noticeDAO.insertNotice("DIS999")).thenReturn(0);

        boolean result = noticeService.insertNotice("DIS999");

        assertThat(result).isFalse();
        verify(noticeDAO).insertNotice("DIS999");
    }

    @Test
    void getNoticeList() {
        List<NoticeVO> notices = List.of(new NoticeVO());
        when(noticeDAO.getNoticeList(B_ID)).thenReturn(notices);

        List<NoticeVO> result = noticeService.getNoticeList(B_ID);

        assertThat(result).isSameAs(notices);
        verify(noticeDAO).getNoticeList(B_ID);
    }

    @Test
    void deleteNoticeAll_returnsDeletedRowCount() {
        when(noticeDAO.deleteNoticeAll()).thenReturn(3);

        int result = noticeService.deleteNoticeAll();

        assertThat(result).isEqualTo(3);
        verify(noticeDAO).deleteNoticeAll();
    }

    @Test
    void updateReadYn_returnsTrueWhenOneRowUpdated() {
        when(noticeDAO.updateReadYn("N001")).thenReturn(1);

        boolean result = noticeService.updateReadYn("N001");

        assertThat(result).isTrue();
        verify(noticeDAO).updateReadYn("N001");
    }

    @Test
    void updateReadYn_returnsFalseWhenNoRowUpdated() {
        when(noticeDAO.updateReadYn("N999")).thenReturn(0);

        boolean result = noticeService.updateReadYn("N999");

        assertThat(result).isFalse();
        verify(noticeDAO).updateReadYn("N999");
    }

    @Test
    void getExpiredIdList() {
        List<String> ids = List.of("DIS011");
        when(noticeDAO.getExpiredIdList(B_ID)).thenReturn(ids);

        List<String> result = noticeService.getExpiredIdList(B_ID);

        assertThat(result).containsExactly("DIS011");
        verify(noticeDAO).getExpiredIdList(B_ID);
    }

    @Test
    void getExpiredCount() {
        when(noticeDAO.getExpiredCount(B_ID)).thenReturn(5);

        int result = noticeService.getExpiredCount(B_ID);

        assertThat(result).isEqualTo(5);
        verify(noticeDAO).getExpiredCount(B_ID);
    }

    @Test
    void getSolidTotal() {
        when(noticeDAO.getSolidTotal(B_ID)).thenReturn(7);

        int result = noticeService.getSolidTotal(B_ID);

        assertThat(result).isEqualTo(7);
        verify(noticeDAO).getSolidTotal(B_ID);
    }

    @Test
    void getLiquidTotal() {
        when(noticeDAO.getLiquidTotal(B_ID)).thenReturn(4);

        int result = noticeService.getLiquidTotal(B_ID);

        assertThat(result).isEqualTo(4);
        verify(noticeDAO).getLiquidTotal(B_ID);
    }

    @Test
    void getMaxOverDay() {
        when(noticeDAO.getMaxOverDay(B_ID)).thenReturn(10);

        int result = noticeService.getMaxOverDay(B_ID);

        assertThat(result).isEqualTo(10);
        verify(noticeDAO).getMaxOverDay(B_ID);
    }
}

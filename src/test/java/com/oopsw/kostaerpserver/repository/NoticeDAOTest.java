package com.oopsw.kostaerpserver.repository;

import com.oopsw.kostaerpserver.vo.NoticeVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

@ActiveProfiles("test")
@SpringBootTest
public class NoticeDAOTest {
    private static final String B_ID = "0000000000";

    @Autowired
    private NoticeDAO noticeDAO;

    @Test
    //특정 사업장의 알림 목록 조회 테스트
    void getNoticeList() {
        List<NoticeVO> result = noticeDAO.getNoticeList(B_ID);
        assertThat(result).isNotNull();
    }

    @Test
    //유통기한이 지난 폐기 대상 ID 목록 조회 테스트
    void getExpiredIdList() {
        List<String> result = noticeDAO.getExpiredIdList(B_ID);
        assertThat(result).isNotNull();
    }

    @Test
    //유통기한 초과 건 수 조회 테스트
    void getExpiredCount() {
        int result = noticeDAO.getExpiredCount(B_ID);
        assertThat(result).isGreaterThanOrEqualTo(0);
    }

    @Test
    //고체 폐기물 총 수량 조회 테스트
    void getSolidTotal() {
        int result = noticeDAO.getSolidTotal(B_ID);
        assertThat(result).isGreaterThanOrEqualTo(0);
    }

    @Test
    //액체 폐기물 총 수량 조회 테스트
    void getLiquidTotal() {
        int result = noticeDAO.getLiquidTotal(B_ID);
        assertThat(result).isGreaterThanOrEqualTo(0);
    }

    @Test
    //가장 오래 지난 유통기한 일수 조회 테스트
    void getMaxOverDay() {
        int result = noticeDAO.getMaxOverDay(B_ID);
        assertThat(result).isGreaterThanOrEqualTo(0);
    }

    @Transactional
    @Test
    //폐기 알림 데이터 추가 테스트
    void insertNotice() {
        String disposalId = "DIS011";
        int result = noticeDAO.insertNotice(disposalId);
        assertThat(result).isEqualTo(1);
        System.out.print("알림 데이터 추가 테스트 성공");
        System.out.println("추가된 데이터 : " + disposalId);
    }

    @Transactional
    @Test
    //알림 읽음 여부 수정 테스트
    void updateReadYn() {
        String noticeId = "N007";
        String bId = "0000000000";
        int result = noticeDAO.updateReadYn(noticeId);
        assertThat(result).isEqualTo(1);
        List<NoticeVO> list = noticeDAO.getNoticeList(bId);
        NoticeVO ndata = null;
        for(NoticeVO notice : list) {
            if(noticeId.equals(notice.getNoticeId())) {
                ndata = notice;
                break;
            }
        }
        assertThat(ndata).isNotNull();
        assertThat(ndata.getReadYn()).isEqualTo("Y");
        System.out.println("알림 읽음 여부 수정 테스트 성공");
        System.out.println("수정된 noticeId : " + noticeId);
        System.out.println("현재 read_yn 값 : " + ndata.getReadYn());
    }

    @Transactional
    @Test
    //알림 데이터 삭제 테스트
    void deleteNoticeAll() {
        int result = noticeDAO.deleteNoticeAll();
        assertThat(result).isGreaterThanOrEqualTo(0);
        System.out.println("알림 데이터 테스트 삭제 성공");
        System.out.println("삭제된 데이터 수 : " + result);
    }
}

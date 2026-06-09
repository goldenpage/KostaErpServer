package com.oopsw.kostaerpserver.repository;

import com.oopsw.kostaerpserver.dto.NoticeListResponse;
import com.oopsw.kostaerpserver.vo.Notice;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
public class NoticeDAOTest {
    private static final String B_ID = "0000000000";

    @Autowired
    private NoticeDAO noticeDAO;

    @Test
    //특정 사업장의 알림 목록 조회 테스트
    void getNoticeList() {
        log.info("getNoticeList 시작 - B_ID: {}", B_ID);
        List<NoticeListResponse> result = noticeDAO.getNoticeList(B_ID);

        log.info("조회된 알림 목록 수: {}", result != null ? result.size() : "null");
        assertThat(result).isNotNull();
        log.info("getNoticeList 성공 & 종료");
    }

    @Test
    //유통기한이 지난 폐기 대상 ID 목록 조회 테스트
    void getExpiredIdList() {
        log.info("getExpiredIdList 시작 - B_ID: {}", B_ID);
        List<String> result = noticeDAO.getExpiredIdList(B_ID);

        log.info("유통기한 만료 대상 ID 목록: {}", result);
        assertThat(result).isNotNull();
        log.info("getExpiredIdList 성공 & 종료");
    }

    @Test
    //유통기한 초과 건 수 조회 테스트
    void getExpiredCount() {
        log.info("getExpiredCount 시작 - B_ID: {}", B_ID);
        int result = noticeDAO.getExpiredCount(B_ID);

        log.info("유통기한 초과 건 수: {}", result);
        assertThat(result).isGreaterThanOrEqualTo(0);
        log.info("getExpiredCount 성공 & 종료");
    }

    @Test
    //고체 폐기물 총 수량 조회 테스트
    void getSolidTotal() {
        log.info("getSolidTotal 시작 - B_ID: {}", B_ID);
        int result = noticeDAO.getSolidTotal(B_ID);

        log.info("고체 폐기물 총 수량: {}", result);
        assertThat(result).isGreaterThanOrEqualTo(0);
        log.info("getSolidTotal 성공 & 종료");
    }

    @Test
    //액체 폐기물 총 수량 조회 테스트
    void getLiquidTotal() {
        log.info("getLiquidTotal 시작 - B_ID: {}", B_ID);
        int result = noticeDAO.getLiquidTotal(B_ID);

        log.info("액체 폐기물 총 수량: {}", result);
        assertThat(result).isGreaterThanOrEqualTo(0);
        log.info("getLiquidTotal 성공 & 종료");
    }

    @Test
    //가장 오래 지난 유통기한 일수 조회 테스트
    void getMaxOverDay() {
        log.info("getMaxOverDay 시작 - B_ID: {}", B_ID);
        int result = noticeDAO.getMaxOverDay(B_ID);

        log.info("가장 오래 지난 유통기한 일수: {}일", result);
        assertThat(result).isGreaterThanOrEqualTo(0);
        log.info("getMaxOverDay 성공 & 종료");
    }

    @Transactional
    @Test
    //폐기 알림 데이터 추가 테스트
    void insertNotice() {
        String disposalId = "DIS012";
        log.info("insertNotice 시작 - DisposalID: {}", disposalId);

        int result = noticeDAO.insertNotice(disposalId);

        log.info("알림 데이터 추가 결과 행 수: {}", result);
        assertThat(result).isEqualTo(1);

        log.info("알림 데이터 추가 테스트 성공 -> 추가된 데이터 ID: {} 종료", disposalId);
    }

    @Transactional
    @Test
    void updateReadYn() {
        String noticeId = "N008";
        String bId = "0000000000";

        log.info("updateReadYn 시작 - noticeId: {}", noticeId);

        int result = noticeDAO.updateReadYn(noticeId);
        assertThat(result).isEqualTo(1);

        List<NoticeListResponse> list = noticeDAO.getNoticeList(bId);

        NoticeListResponse ndata = null;
        for (NoticeListResponse notice : list) {
            if (noticeId.equals(notice.getNoticeId())) {
                ndata = notice;
                break;
            }
        }

        assertThat(ndata).isNull();

        log.info("알림 읽음 여부 수정 테스트 성공 - 읽음 처리된 noticeId는 미확인 목록에서 제외됨: {}", noticeId);
    }

    @Transactional
    @Test
    //알림 데이터 삭제 테스트
    void deleteNoticeAll() {
        log.info("deleteNoticeAll 시작");

        int result = noticeDAO.deleteNoticeAll();

        assertThat(result).isGreaterThanOrEqualTo(0);

        log.info("알림 데이터 테스트 전체 삭제 성공 -> 삭제된 데이터 수: {}건 종료", result);
    }
}

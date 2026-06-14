package com.oopsw.kostaerpserver.repository.entity;

import com.oopsw.kostaerpserver.repository.entity.outofstocknotice.OutOfStockNotice;
import com.oopsw.kostaerpserver.repository.entity.outofstocknotice.OutOfStockNoticeRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
public class OutOfStockNoticeDAOTest {
    @Autowired
    private OutOfStockNoticeRepository outOfStockNoticeRepository;

    @Test
    public void stockNoticeTest() {
        outOfStockNoticeRepository.save(OutOfStockNotice.builder().
                noticeDate(LocalDateTime.now()).
                noticeContent(" 모두 소진됨").
                foodMaterialName("단무지").
                remainStockAmount(0).
                bId("1234567890").
                readYn("Y").
                build());

        outOfStockNoticeRepository.save(OutOfStockNotice.builder().
                noticeDate(LocalDateTime.now()).
                noticeContent(" 얼마 남지 않음").
                foodMaterialName("김").
                remainStockAmount(3).
                bId("1234567890").
                readYn("N").
                build());

        log.info("saved notice = {}", outOfStockNoticeRepository);
    }

    @Test
    public void findByBIdAndReadYnOrderByNoticeDateDescTest(){
        outOfStockNoticeRepository.save(OutOfStockNotice.builder().
                noticeDate(LocalDateTime.now()).
                noticeContent(" 모두 소진됨").
                foodMaterialName("단무지").
                remainStockAmount(0).
                bId("1234567890").
                readYn("Y").
                build());

        outOfStockNoticeRepository.save(OutOfStockNotice.builder().
                noticeDate(LocalDateTime.now()).
                noticeContent(" 얼마 남지 않음").
                foodMaterialName("김").
                remainStockAmount(3).
                bId("1234567890").
                readYn("N").
                build());

        List<OutOfStockNotice> list = outOfStockNoticeRepository.
                findByBIdAndReadYnOrderByNoticeDateDesc("1234567890", "Y");
        log.info("list = {}", list);
    }

    @Test
    public void countByBIdAndReadYnTest(){
        outOfStockNoticeRepository.save(OutOfStockNotice.builder().
                noticeDate(LocalDateTime.now()).
                noticeContent(" 얼마 남지 않음").
                foodMaterialName("김").
                remainStockAmount(3).
                bId("1234567890").
                readYn("Y").
                build());

        int result = outOfStockNoticeRepository.
                countByBIdAndReadYn("1234567890", "Y");
        log.info("result = {}", result);
    }

    @Test
    public void existsTodayNoticeTest() {
        // given: 오늘 날짜로 단무지 알림 저장
        outOfStockNoticeRepository.save(OutOfStockNotice.builder().
                noticeDate(LocalDateTime.now()).
                noticeContent(" 얼마 남지 않음").
                foodMaterialName("단무지").
                remainStockAmount(2).
                bId("1234567890").
                readYn("N").
                build());

        boolean exists = outOfStockNoticeRepository.
                existsTodayNotice("1234567890", "단무지");

        log.info("existsTodayNotice(단무지) = {}", exists);
    }
}

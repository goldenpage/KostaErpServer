package com.oopsw.kostaerpserver.repository.entity;

import com.oopsw.kostaerpserver.repository.entity.outofstocknotice.OutOfStockNotice;
import com.oopsw.kostaerpserver.repository.entity.outofstocknotice.OutOfStockNoticeRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

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
                build());

        outOfStockNoticeRepository.save(OutOfStockNotice.builder().
                noticeDate(LocalDateTime.now()).
                noticeContent(" 얼마 남지 않음").
                foodMaterialName("김").
                remainStockAmount(3).
                build());

        log.info("saved notice = {}", outOfStockNoticeRepository);
    }
}

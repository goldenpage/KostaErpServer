package com.oopsw.kostaerpserver.repository.entity;

import com.oopsw.kostaerpserver.repository.entity.expdate.ExpNotice;
import com.oopsw.kostaerpserver.repository.entity.expdate.ExpNoticeRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ExpNoticeDAOTest {
    @Autowired
    private ExpNoticeRepository expNoticeRepository;

    private final String testBId = "9999999999";

    @Test
    void saveExpNoticeTest() {
        ExpNotice expNotice = ExpNotice.builder()
                .bId(testBId)
                .expAlert(true)
                .expDays(3)
                .build();

        ExpNotice saved = expNoticeRepository.save(expNotice);

        log.info("saved expNoticeId = {}", saved.getExpNoticeId());
        log.info("saved bId = {}", saved.getBId());
        log.info("saved expAlert = {}", saved.isExpAlert());
        log.info("saved expDays = {}", saved.getExpDays());

        Assertions.assertNotNull(saved);
        Assertions.assertTrue(saved.getExpNoticeId() > 0);
        Assertions.assertEquals(testBId, saved.getBId());
        Assertions.assertTrue(saved.isExpAlert());
        Assertions.assertEquals(3, saved.getExpDays());
    }

    @Test
    void findByBIdTest() {
        ExpNotice expNotice = ExpNotice.builder()
                .bId(testBId)
                .expAlert(true)
                .expDays(3)
                .build();

        expNoticeRepository.save(expNotice);

        ExpNotice result = expNoticeRepository.findByBId(testBId)
                .orElse(null);

        log.info("findByBId result = {}", result);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(testBId, result.getBId());
        Assertions.assertTrue(result.isExpAlert());
        Assertions.assertEquals(3, result.getExpDays());
    }

    @Test
    void updateExpNoticeTest() {
        ExpNotice expNotice = ExpNotice.builder()
                .bId(testBId)
                .expAlert(true)
                .expDays(3)
                .build();

        expNoticeRepository.save(expNotice);

        ExpNotice saved = expNoticeRepository.findByBId(testBId)
                .orElseThrow();

        saved.update(false, 5);

        log.info("updated expAlert = {}", saved.isExpAlert());
        log.info("updated expDays = {}", saved.getExpDays());

        Assertions.assertFalse(saved.isExpAlert());
        Assertions.assertEquals(5, saved.getExpDays());
    }

    @Test
    void expDaysTest() {
        ExpNotice expNotice = ExpNotice.builder()
                .bId(testBId)
                .expAlert(true)
                .expDays(3)
                .build();

        expNoticeRepository.save(expNotice);

        ExpNotice saved = expNoticeRepository.findByBId(testBId)
                .orElseThrow();

        saved.update(true, 0);

        log.info("보정된 expDays = {}", saved.getExpDays());

        Assertions.assertEquals(1, saved.getExpDays());
    }
}
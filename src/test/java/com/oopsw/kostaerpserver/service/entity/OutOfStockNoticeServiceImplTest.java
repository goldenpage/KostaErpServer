package com.oopsw.kostaerpserver.service.entity;

import com.oopsw.kostaerpserver.vo.entity.OutOfStockNoticeVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
public class OutOfStockNoticeServiceImplTest {
    @Autowired
    OutOfStockNoticeServiceImpl outOfStockNoticeServiceImpl;

    @Test
    public void addOutOfStockNoticeTest() {
        Assertions.assertTrue(outOfStockNoticeServiceImpl.addOutOfStockNotice(OutOfStockNoticeVO.builder().
                noticeDate(String.valueOf(LocalDateTime.now())).
                noticeContent(" 모두 소진됨").
                foodMaterialName("닭가슴살").
                remainStockAmount(0).
                build()));
/* 안되는 테스트 - null값 허용안함
        Assertions.assertFalse(outOfStockNoticeService.addOutOfStockNotice(OutOfStockNoticeVO.builder().
                noticeDate(String.valueOf(LocalDateTime.now())).
                noticeContent(" 얼마 남지 않음").
                build()));
  */
    }
}

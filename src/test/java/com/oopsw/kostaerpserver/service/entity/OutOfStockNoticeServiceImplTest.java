package com.oopsw.kostaerpserver.service.entity;

import com.oopsw.kostaerpserver.dto.outofstock.OutOfStockNoticeResponse;
import com.oopsw.kostaerpserver.repository.entity.outofstocknotice.OutOfStockNotice;
import com.oopsw.kostaerpserver.repository.entity.outofstocknotice.OutOfStockNoticeRepository;
import com.oopsw.kostaerpserver.vo.entity.OutOfStockNoticeVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
public class OutOfStockNoticeServiceImplTest {
    @Autowired
    OutOfStockNoticeServiceImpl outOfStockNoticeServiceImpl;
    @Autowired
    private OutOfStockNoticeRepository outOfStockNoticeRepository;

    //    @Test
    public void addOutOfStockNoticeTest() {
        Assertions.assertTrue(outOfStockNoticeServiceImpl.addOutOfStockNotice(OutOfStockNoticeVO.builder().
                noticeDate(String.valueOf(LocalDateTime.now())).
                noticeContent(" 모두 소진됨").
                foodMaterialName("닭가슴살").
                remainStockAmount(0).
                bId("1234567890").
                readYn("Y").
                build()));

        log.info("saved notice = {}", outOfStockNoticeServiceImpl);
    }

//    @Test
    public void getUnreadListTest(){
        outOfStockNoticeRepository.save(com.oopsw.kostaerpserver.repository.entity.outofstocknotice.OutOfStockNotice.builder().
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

        List<OutOfStockNoticeResponse> list = outOfStockNoticeServiceImpl.getUnreadList("1234567890");
        log.info("list = {}", list);
        Assertions.assertTrue(list.size() > 0, "list");
    }

    @Test
    public void getUnreadCountTest(){
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

        int result = outOfStockNoticeServiceImpl.getUnreadCount("1234567890");
        log.info("count = {}", result);
        Assertions.assertEquals(1, result);
    }

//    @Test
    public void markAsReadTest(){
        boolean result = outOfStockNoticeServiceImpl.markAsRead(2);
        log.info("result = {}", result);
        Assertions.assertTrue(true);
    }
}

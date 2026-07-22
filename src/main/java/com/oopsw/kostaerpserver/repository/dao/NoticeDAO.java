package com.oopsw.kostaerpserver.repository.dao;

import com.oopsw.kostaerpserver.dto.notice.NoticeListResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NoticeDAO {
    int insertNotice(@Param("disposalId") String disposalId);
    List<NoticeListResponse> getNoticeList(@Param("bId") String bId);
    int deleteNoticeAll();
    int updateReadYn(@Param("noticeId") String noticeId);
    List<String> getExpiredIdList(@Param("bId") String bId);
    int getExpiredCount(@Param("bId") String bId);
    int getSolidTotal(@Param("bId") String bId);
    int getLiquidTotal(@Param("bId") String bId);
    int getMaxOverDay(@Param("bId") String bId);
}

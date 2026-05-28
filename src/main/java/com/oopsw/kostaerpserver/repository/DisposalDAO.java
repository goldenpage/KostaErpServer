package com.oopsw.kostaerpserver.repository;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.oopsw.kostaerpserver.vo.Disposal;

@Mapper
public interface DisposalDAO {

    List<Disposal> getDisposals();

    List<String> getFoodMaterialNames();

    List<String> getCategories();

    List<Disposal> getDisposalsFilteredPaging(
            @Param("bId") String bId,
            @Param("offset") int offset,
            @Param("size") int size);

    int getDisposalCount(@Param("bId") String bId);

    int getTotalCount(@Param("bId") String bId);

    List<String> getReasons();

    List<Disposal> getDisposalsByCategoryAndBId(
            @Param("category") String category,
            @Param("bId") String bId);

    List<Disposal> getDisposalsPaging(
            @Param("bId") String bId,
            @Param("offset") int offset,
            @Param("size") int size);

    int updateReason(
            @Param("disposalId") String disposalId,
            @Param("reasonId") String reasonId);

    List<String> getExpiredDisposalIds(@Param("bId") String bId);

    double getDisposalRate(
            @Param("bId") String bId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    int getTotalDisposalPrice(
            @Param("bId") String bId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    List<Disposal> getTop3DisposalItems(
            @Param("bId") String bId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    List<Disposal> getDisposalReasonRatio(
            @Param("bId") String bId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    List<Disposal> selectDailyDisposalAmount(
            @Param("bId") String bId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    List<Disposal> selectDailyDisposalByType(
            @Param("bId") String bId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}

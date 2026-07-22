package com.oopsw.kostaerpserver.repository.dao;

import java.time.LocalDate;
import java.util.List;

import com.oopsw.kostaerpserver.dto.disposal.DisposalCreateRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.oopsw.kostaerpserver.vo.Disposal;
import com.oopsw.kostaerpserver.dto.disposal.DisposalListResponse;
import org.springframework.security.core.parameters.P;

@Mapper
public interface DisposalDAO {

    List<DisposalListResponse> getDisposals();

    List<String> getFoodMaterialNames();

    List<String> getCategories(@Param("bId") String bId);

    List<DisposalListResponse> getDisposalsFilteredPaging(
            @Param("bId") String bId,
            @Param("offset") int offset,
            @Param("size") int size);

    int getDisposalCount(@Param("bId") String bId);

    int getTotalCount(@Param("bId") String bId);

    List<String> getReasons();

    List<DisposalListResponse> getDisposalsByCategoryAndBId(
            @Param("category") String category,
            @Param("bId") String bId);

    List<DisposalListResponse> getDisposalsPaging(
            @Param("bId") String bId,
            @Param("offset") int offset,
            @Param("size") int size);

    int updateReason(
            @Param("disposalId") String disposalId,
            @Param("reasonId") String reasonId);

    int insertDisposal(DisposalCreateRequest request);

    int decreaseTotalWeight(
            @Param("foodMaterialId") String foodMaterialId,
            @Param("bId") String bId,
            @Param("disposalCountAll") int disposalCountAll
    );

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
package com.oopsw.kostaerpserver.repository;

import com.oopsw.kostaerpserver.vo.FoodMaterial;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FoodMaterialDAO {

    int getFoodMaterialCount(@Param("bId") String bId);

    List<FoodMaterial> getFoodMaterialListIdDesc(
            @Param("bId") String bId,
            @Param("pageSize") int pageSize,
            @Param("offset") int offset
    );

    List<FoodMaterial> getFoodMaterialListIdAsc(
            @Param("bId") String bId,
            @Param("pageSize") int pageSize,
            @Param("offset") int offset
    );

    List<FoodMaterial> getFoodMaterialListExpAsc(
            @Param("bId") String bId,
            @Param("pageSize") int pageSize,
            @Param("offset") int offset
    );

    List<FoodMaterial> getFoodMaterialListExpDesc(
            @Param("bId") String bId,
            @Param("pageSize") int pageSize,
            @Param("offset") int offset
    );

    List<FoodMaterial> getFoodMaterialByName(
            @Param("bId") String bId,
            @Param("foodMaterialName") String foodMaterialName
    );

    int deleteUsedByFoodMaterial(@Param("foodMaterialId") String foodMaterialId);

    int deleteDisposalsByFoodMaterial(@Param("foodMaterialId") String foodMaterialId);

    int deleteFoodMaterial(
            @Param("foodMaterialId") String foodMaterialId,
            @Param("bId") String bId
    );
}
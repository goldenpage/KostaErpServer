package com.oopsw.kostaerpserver.repository;

import com.oopsw.kostaerpserver.vo.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenuDAO {

    List<Menu> getMenuList(@Param("bId") String bId);

    List<Menu> getMenuDetail(@Param("menuId") String menuId);

    int getLackMaterialCount(
            @Param("menuId") String menuId,
            @Param("saleCount") int saleCount
    );

    int updateFoodMaterialAfterSale(
            @Param("menuId") String menuId,
            @Param("saleCount") int saleCount,
            @Param("bId") String bId
    );
}
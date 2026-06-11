package com.oopsw.kostaerpserver.repository.entity.salesrecord;

import org.springframework.data.jpa.repository.JpaRepository;
import com.oopsw.kostaerpserver.repository.entity.salesrecord.Menu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, String> {
    @Query(value = "SELECT * FROM MENUS WHERE menu_Id = :menuId", nativeQuery = true)
    Optional<Menu> findByMenuIdNative(@Param("menuId") String menuId);

}

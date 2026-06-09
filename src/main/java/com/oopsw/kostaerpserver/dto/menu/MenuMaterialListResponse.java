package com.oopsw.kostaerpserver.dto.menu;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenuMaterialListResponse {
    private String menuId;
    private List<MenuMaterialResponse> materialList;
    private int totalCount;
}
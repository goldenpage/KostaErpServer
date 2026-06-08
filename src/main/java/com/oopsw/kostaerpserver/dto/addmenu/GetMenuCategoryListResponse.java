package com.oopsw.kostaerpserver.dto.addmenu;

import com.oopsw.kostaerpserver.vo.MenuCategory;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetMenuCategoryListResponse {
    private String menuCategoryId;
    private String menuCategory;

    public static GetMenuCategoryListResponse from(MenuCategory vo) {
        return GetMenuCategoryListResponse.builder()
                .menuCategoryId(vo.getMenuCategoryId())
                .menuCategory(vo.getMenuCategory())
                .build();
    }

    public static List<GetMenuCategoryListResponse> fromList(List<MenuCategory> voList) {
        return voList.stream()
                .map(GetMenuCategoryListResponse::from)
                .collect(Collectors.toList());
    }
}

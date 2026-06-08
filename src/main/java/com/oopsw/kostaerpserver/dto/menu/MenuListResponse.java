package com.oopsw.kostaerpserver.dto.menu;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenuListResponse {
    private List<MenuResponse> menuList;
    private int totalCount;
    private String bId;
}
package com.oopsw.kostaerpserver.vo;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Menu {
    private String menuId;
    private String menuName;
    private int menuPrice;
    private String menuCategoryId;
}

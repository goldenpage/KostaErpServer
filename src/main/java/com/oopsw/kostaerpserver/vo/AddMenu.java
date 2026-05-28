package com.oopsw.kostaerpserver.vo;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddMenu {
    private String menuId;
    private String menuName;
    private int menuPrice;
    private String menuCategoryId;
}

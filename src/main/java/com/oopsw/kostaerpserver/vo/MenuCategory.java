package com.oopsw.kostaerpserver.vo;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class MenuCategory {
    private String menuCategoryId;
    private String menuCategory;
    private String bId;
}

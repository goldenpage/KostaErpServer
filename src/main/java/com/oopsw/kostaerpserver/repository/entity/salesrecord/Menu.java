package com.oopsw.kostaerpserver.repository.entity.salesrecord;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "MENUS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Menu {

    @Id
    @Column(name = "menu_Id")
    private String menuId;

    @Column(name = "menuName")
    private String menuName;

    @Column(name = "menuPrice")
    private int menuPrice;
}
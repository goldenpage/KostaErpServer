package com.oopsw.kostaerpserver.repository.entity.salesrecord;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "MENUC")
@Getter
@Setter
public class MenuCategory {

    @Id
    @Column(name = "menuCategory_Id", length = 50)
    private String menuCategoryId;

    @Column(name = "menuCategory", nullable = false, length = 50)
    private String menuCategory;

    @Column(name = "bId", nullable = false, length = 50)
    private String bId;
}
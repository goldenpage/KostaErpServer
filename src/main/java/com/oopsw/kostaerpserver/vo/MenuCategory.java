package com.oopsw.kostaerpserver.vo;

public class MenuCategory {
    private String menuCategoryId;
    private String menuCategory;
    private String bId;

    public MenuCategory() {}

    public MenuCategory(String menuCategory, String bId) {
        this.menuCategory = menuCategory;
        this.bId = bId;
    }

    public String getMenuCategoryId() { return menuCategoryId; }
    public void setMenuCategoryId(String menuCategoryId) { this.menuCategoryId = menuCategoryId; }

    public String getMenuCategory() { return menuCategory; }
    public void setMenuCategory(String menuCategory) { this.menuCategory = menuCategory; }

    public String getbId() { return bId; }
    public void setbId(String bId) { this.bId = bId; }

    @Override
    public String toString() {
        return "MenuCategoryVO{" +
                "menuCategoryId='" + menuCategoryId + '\'' +
                ", menuCategory='" + menuCategory + '\'' +
                ", bId='" + bId + '\'' +
                '}';
    }
}

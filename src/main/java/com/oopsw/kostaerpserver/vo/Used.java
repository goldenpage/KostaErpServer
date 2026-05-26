package com.oopsw.kostaerpserver.vo;

public class Used {
    private String usedMaterialId;
    private int usedCount;
    private String foodMaterialId;
    private String menuId;

    public Used() {}

    public Used(int usedCount, String foodMaterialId, String menuId) {
        this.usedCount = usedCount;
        this.foodMaterialId = foodMaterialId;
        this.menuId = menuId;
    }

    public String getUsedMaterialId() { return usedMaterialId; }
    public void setUsedMaterialId(String usedMaterialId) { this.usedMaterialId = usedMaterialId; }

    public int getUsedCount() { return usedCount; }
    public void setUsedCount(int usedCount) { this.usedCount = usedCount; }

    public String getFoodMaterialId() { return foodMaterialId; }
    public void setFoodMaterialId(String foodMaterialId) { this.foodMaterialId = foodMaterialId; }

    public String getMenuId() { return menuId; }
    public void setMenuId(String menuId) { this.menuId = menuId; }

    @Override
    public String toString() {
        return "UsedVO{" +
                "usedMaterialId='" + usedMaterialId + '\'' +
                ", usedCount=" + usedCount +
                ", foodMaterialId='" + foodMaterialId + '\'' +
                ", menuId='" + menuId + '\'' +
                '}';
    }
}

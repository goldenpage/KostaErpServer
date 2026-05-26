package com.oopsw.kostaerpserver.vo;

import java.util.Date;

public class FoodMaterial {
    private String foodMaterialId;
    private String foodMaterialName;
    private String foodCategory;
    private int foodMaterialCount;
    private int foodMaterialCountAll;
    private int foodMaterialPrice;
    private String vender;
    private String foodMaterialType;
    private Date incomeDate;
    private Date expirationDate;
    private String bId;
    private int ranking;
    private int totalExpense;

    public FoodMaterial() {}

    public FoodMaterial(String foodMaterialName, String foodCategory, int foodMaterialCount,
                          int foodMaterialCountAll, int foodMaterialPrice, String vender,
                          String foodMaterialType, Date incomeDate, Date expirationDate, String bId) {
        this.foodMaterialName = foodMaterialName;
        this.foodCategory = foodCategory;
        this.foodMaterialCount = foodMaterialCount;
        this.foodMaterialCountAll = foodMaterialCountAll;
        this.foodMaterialPrice = foodMaterialPrice;
        this.vender = vender;
        this.foodMaterialType = foodMaterialType;
        this.incomeDate = incomeDate;
        this.expirationDate = expirationDate;
        this.bId = bId;
    }

    public String getFoodMaterialId() { return foodMaterialId; }
    public void setFoodMaterialId(String foodMaterialId) { this.foodMaterialId = foodMaterialId; }

    public String getFoodMaterialName() { return foodMaterialName; }
    public void setFoodMaterialName(String foodMaterialName) { this.foodMaterialName = foodMaterialName; }

    public String getFoodCategory() { return foodCategory; }
    public void setFoodCategory(String foodCategory) { this.foodCategory = foodCategory; }

    public int getFoodMaterialCount() { return foodMaterialCount; }
    public void setFoodMaterialCount(int foodMaterialCount) { this.foodMaterialCount = foodMaterialCount; }

    public int getFoodMaterialCountAll() { return foodMaterialCountAll; }
    public void setFoodMaterialCountAll(int foodMaterialCountAll) { this.foodMaterialCountAll = foodMaterialCountAll; }

    public int getFoodMaterialPrice() { return foodMaterialPrice; }
    public void setFoodMaterialPrice(int foodMaterialPrice) { this.foodMaterialPrice = foodMaterialPrice; }

    public String getVender() { return vender; }
    public void setVender(String vender) { this.vender = vender; }

    public String getFoodMaterialType() { return foodMaterialType; }
    public void setFoodMaterialType(String foodMaterialType) { this.foodMaterialType = foodMaterialType; }

    public Date getIncomeDate() { return incomeDate; }
    public void setIncomeDate(Date incomeDate) { this.incomeDate = incomeDate; }

    public Date getExpirationDate() { return expirationDate; }
    public void setExpirationDate(Date expirationDate) { this.expirationDate = expirationDate; }

    public String getbId() { return bId; }
    public void setbId(String bId) { this.bId = bId; }

    public int getRanking() { return ranking; }
    public void setRanking(int ranking) { this.ranking = ranking; }

    public int getTotalExpense() { return totalExpense; }
    public void setTotalExpense(int totalExpense) { this.totalExpense = totalExpense; }

    @Override
    public String toString() {
        return "FoodMaterialVO{" +
                "foodMaterialId='" + foodMaterialId + '\'' +
                ", foodMaterialName='" + foodMaterialName + '\'' +
                ", foodCategory='" + foodCategory + '\'' +
                ", foodMaterialCount=" + foodMaterialCount +
                ", foodMaterialCountAll=" + foodMaterialCountAll +
                ", foodMaterialPrice=" + foodMaterialPrice +
                ", vender='" + vender + '\'' +
                ", foodMaterialType='" + foodMaterialType + '\'' +
                ", incomeDate=" + incomeDate +
                ", expirationDate=" + expirationDate +
                ", bId='" + bId + '\'' +
                '}';
    }
}

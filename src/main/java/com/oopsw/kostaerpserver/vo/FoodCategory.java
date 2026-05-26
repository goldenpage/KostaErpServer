package com.oopsw.kostaerpserver.vo;

public class FoodCategory {
    private String foodCategoryId;
    private String foodCategory;

    public FoodCategory() {}

    public FoodCategory(String foodCategoryId, String foodCategory) {
        this.foodCategoryId = foodCategoryId;
        this.foodCategory = foodCategory;
    }

    public String getFoodCategoryId() { return foodCategoryId; }
    public void setFoodCategoryId(String foodCategoryId) { this.foodCategoryId = foodCategoryId; }

    public String getFoodCategory() { return foodCategory; }
    public void setFoodCategory(String foodCategory) { this.foodCategory = foodCategory; }

    @Override
    public String toString() {
        return "FoodCategoryVO{" +
                "foodCategoryId='" + foodCategoryId + '\'' +
                ", foodCategory='" + foodCategory + '\'' +
                '}';
    }
}

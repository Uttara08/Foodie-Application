package com.niit.customerservice.domain;

public class Food {

    private String itemName;
    private String foodDescription;
    private float price;

    public Food() {
    }

    public Food( String itemName, String foodDescription, float price) {

        this.itemName = itemName;
        this.foodDescription = foodDescription;
        this.price = price;
    }



    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getFoodDescription() {
        return foodDescription;
    }

    public void setFoodDescription(String foodDescription) {
        this.foodDescription = foodDescription;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Food{" +

                ", itemName='" + itemName + '\'' +
                ", foodDescription='" + foodDescription + '\'' +
                ", price=" + price +
                '}';
    }
}

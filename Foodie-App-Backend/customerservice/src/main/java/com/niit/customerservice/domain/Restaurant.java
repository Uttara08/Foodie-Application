package com.niit.customerservice.domain;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

public class Restaurant {

        private String restaurantId;
        private String restaurantName;
        private String location;
        private List<Food> foodList;

        public Restaurant() {
        }

        public Restaurant(String restaurantId, String restaurantName, String location, List<Food> foodList) {
         this.restaurantId = restaurantId;
         this.restaurantName = restaurantName;
         this.location = location;
         this.foodList = foodList;
        }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "restaurantId='" + restaurantId + '\'' +
                ", restaurantName='" + restaurantName + '\'' +
                ", location='" + location + '\'' +
                ", foodList=" + foodList +
                '}';
    }
}

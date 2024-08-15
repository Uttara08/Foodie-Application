package com.niit.restaurantservice.exception;

public class FoodListNotFoundException extends RuntimeException {
    public FoodListNotFoundException(String message) {
        super(message);
    }
}

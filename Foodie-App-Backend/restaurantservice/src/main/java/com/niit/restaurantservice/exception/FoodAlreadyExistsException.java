package com.niit.restaurantservice.exception;

public class FoodAlreadyExistsException extends RuntimeException{
    public FoodAlreadyExistsException(String message) {
        super(message);
}
}

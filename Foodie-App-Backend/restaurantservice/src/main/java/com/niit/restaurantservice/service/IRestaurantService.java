package com.niit.restaurantservice.service;

import com.niit.restaurantservice.domain.Food;
import com.niit.restaurantservice.domain.Restaurant;
import com.niit.restaurantservice.exception.*;

import java.util.List;
import java.util.Optional;

public interface IRestaurantService {
    public Restaurant addRestaurant(Restaurant restaurant);
    public List<Food> getAllFood(String restaurantId) throws FoodItemNotFoundException, RestaurantNotFoundException;
    public List<Restaurant> getAllRestaurant();
    public Restaurant addFoodToRestaurant(Food food, String restaurantId) throws RestaurantNotFoundException, FoodAlreadyExistsException;
    public Restaurant updateFoodToRestaurant(Food food, String restaurantId) throws RestaurantNotFoundException, FoodAlreadyExistsException;
    List<Restaurant> getRestaurantByitemName(String itemName) ;
    Restaurant deleteFoodToRestaurant(String email, String itemName) throws RestaurantNotFoundException, FoodListNotFoundException;
    public Optional<Restaurant> getOneRestaurant(String email);
    public Food getFoodByName(String emailId,String foodName) throws FoodItemNotFoundException;
}

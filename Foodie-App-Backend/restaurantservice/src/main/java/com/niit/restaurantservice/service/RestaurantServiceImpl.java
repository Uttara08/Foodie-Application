package com.niit.restaurantservice.service;

import com.niit.restaurantservice.domain.Food;
import com.niit.restaurantservice.domain.Restaurant;
import com.niit.restaurantservice.exception.*;
import com.niit.restaurantservice.proxy.RestaurantProxy;
import com.niit.restaurantservice.repository.IRestaurantRepository;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.*;

@Service
public class RestaurantServiceImpl implements IRestaurantService {
    private final IRestaurantRepository restaurantRepository;
    private final RestaurantProxy restaurantProxy;

    public RestaurantServiceImpl(IRestaurantRepository restaurantRepository, RestaurantProxy restaurantProxy) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantProxy = restaurantProxy;
    }

    @Override
    public Restaurant addRestaurant(Restaurant restaurant) {
        if (restaurantRepository.findById(restaurant.getEmailId()).isPresent()) {
            throw new RestaurantAlreadyExistsException(" Restaurant in the given EmailId exists");
        } else {
            Restaurant insertedRestaurant = restaurantRepository.save(restaurant);
            System.out.println(insertedRestaurant);
            restaurantProxy.addCustomer(insertedRestaurant);
            return restaurantRepository.save(restaurant);
        }
    }

    @Override
    public List<Food> getAllFood(String restaurantName) throws FoodItemNotFoundException, RestaurantNotFoundException {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findByName(restaurantName);
        if (restaurantOptional.isPresent()) {
            Restaurant restaurant = restaurantOptional.get();
            List<Food> foodList = restaurant.getFoodList();
            if (foodList == null)
                throw new FoodItemNotFoundException("No FoodItem added to the list" + restaurantName);
            else
                return foodList;
        } else
            throw new RestaurantNotFoundException("Customer with given restaurantId is not found");
    }

    @Override
    public List<Restaurant> getAllRestaurant() {
        return (List<Restaurant>) restaurantRepository.findAll();
    }

    @Override
    public Restaurant deleteFoodToRestaurant(String email, String itemName) throws RestaurantNotFoundException, FoodListNotFoundException {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(email);
        if (restaurantOptional.isPresent()) {
            Restaurant restaurantDetails = restaurantOptional.get();
            List<Food> dishList = restaurantDetails.getFoodList();
            if (dishList == null) {
                throw new FoodListNotFoundException("No dishes assigned to this restaurant");
            } else {
                boolean dishFound = false;
                for (Iterator<Food> iterator = dishList.iterator();
                     iterator.hasNext(); ) {
                    Food dish = iterator.next();
                    if (dish.getItemName().equals(itemName)) {
                        iterator.remove();
                        dishFound = true;
                        break;
                    }
                }

                if (!dishFound) {
                    throw new FoodListNotFoundException("Dish with name " + itemName + " not found for this restaurant");
                }
            }
            return restaurantRepository.save(restaurantDetails);
        } else {
            throw new RestaurantNotFoundException("Restaurant not found in this name " + email);
        }
    }

    @Override
    public Optional<Restaurant> getOneRestaurant(String email) {
        return restaurantRepository.findById(email);
    }


    @Override
    public Restaurant addFoodToRestaurant(Food food, String restaurantemailId) throws RestaurantNotFoundException, FoodAlreadyExistsException {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(restaurantemailId);
        if (restaurantOptional.isPresent()) {
            Restaurant restaurant = restaurantOptional.get();
            List<Food> foodList = restaurant.getFoodList();
            if (foodList == null) {
                restaurant.setFoodList(Collections.singletonList(food));
            } else {
                boolean flag = false;
                for (Food existingFood : foodList) {
                    if (existingFood.getItemName() != null && existingFood.getItemName().equals(food.getItemName())) {
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    throw new FoodAlreadyExistsException("The given food name already exists");
                } else {
                    foodList.add(food);
                    restaurant.setFoodList(foodList);
                }
            }
            return restaurantRepository.save(restaurant);
        }
        throw new RestaurantNotFoundException("Cannot find restaurant");
    }


    @Override
    public Restaurant updateFoodToRestaurant(Food food, String restaurantId) throws RestaurantNotFoundException, FoodListNotFoundException {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(restaurantId);
        if (restaurantOptional.isPresent()) {
            Restaurant restaurant = restaurantOptional.get();
            List<Food> foodList = restaurant.getFoodList();
            if (foodList == null) {
                throw new FoodListNotFoundException("No food items found in the restaurant");
            } else {
                boolean foodFound = false;
                for (Food existingFood : foodList) {
                    if (existingFood.getItemName().equals(food.getItemName())) {
                        existingFood.setFoodDescription(food.getFoodDescription());
                        existingFood.setPrice(food.getPrice());
                        foodFound = true;
                        break;
                    }
                }
                if (!foodFound) {
                    throw new FoodListNotFoundException("Food item not found");
                }
            }
            return restaurantRepository.save(restaurant);
        } else {
            throw new RestaurantNotFoundException("Restaurant not found");
        }
    }


    @Override
    public List<Restaurant> getRestaurantByitemName(String itemName) {
        List<Restaurant> restaurantList = restaurantRepository.getAllitemName(itemName);
        if (restaurantList.isEmpty()) {
            throw new RestaurantNotFoundException("No Restaurant is present");
        } else {
            return restaurantList;
        }
    }

    @Override
    public Food getFoodByName(String emailid, String foodName) throws FoodItemNotFoundException {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(emailid);
        if (restaurantOptional.isPresent()) {
            Restaurant restaurant = restaurantOptional.get();
            List<Food> foodList = restaurant.getFoodList();
            if (foodList.isEmpty()) {
                throw new FoodItemNotFoundException("FOod not FOund");
            } else {
                for (Food food : foodList) {
                    if (food.getItemName().equals(foodName))
                        return food;
                }
            }
        } else {
            throw new RuntimeException();
        }
        return null;
    }
}
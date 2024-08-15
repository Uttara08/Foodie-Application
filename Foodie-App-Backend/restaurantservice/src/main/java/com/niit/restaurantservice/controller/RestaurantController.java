package com.niit.restaurantservice.controller;

import com.niit.restaurantservice.domain.Food;
import com.niit.restaurantservice.domain.Restaurant;
import com.niit.restaurantservice.exception.*;
import com.niit.restaurantservice.service.IRestaurantService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2")
public class RestaurantController {
    private final IRestaurantService restaurantService;
@Autowired
    public RestaurantController(IRestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping("/addRestaurant")
    public ResponseEntity<?> addRestaurant(@RequestBody Restaurant restaurant){
        ResponseEntity responseEntity;
        try {
            Restaurant addedRestaurant =restaurantService.addRestaurant(restaurant);
            responseEntity=new ResponseEntity<>(addedRestaurant, HttpStatus.ACCEPTED);
        }
        catch (RestaurantAlreadyExistsException exception){
            responseEntity=new ResponseEntity<>(exception.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }
    @GetMapping("/restaurants")
    public ResponseEntity<?> getAllRestaurant(){
            List<Restaurant> restaurant=restaurantService.getAllRestaurant();
            return new ResponseEntity<>(restaurant,HttpStatus.ACCEPTED);
    }

    @GetMapping("/viewFood/{restId}")
    public ResponseEntity<?> getAllFood(@PathVariable String restId) throws FoodItemNotFoundException {
        return new ResponseEntity<>(restaurantService.getAllFood(restId), HttpStatus.OK);
    }


    @GetMapping("/viewRestaurantByitemName/{itemName}")
    public ResponseEntity<?> showRestaurantByitemName(@PathVariable String itemName){
        ResponseEntity responseEntity;
        try {
            responseEntity= new ResponseEntity<>(restaurantService.getRestaurantByitemName(itemName), HttpStatus.ACCEPTED);
        } catch (RestaurantNotFoundException exception) {
            responseEntity=new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

}


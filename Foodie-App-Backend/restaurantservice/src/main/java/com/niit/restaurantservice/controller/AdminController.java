package com.niit.restaurantservice.controller;

import com.niit.restaurantservice.domain.Food;
import com.niit.restaurantservice.domain.Restaurant;
import com.niit.restaurantservice.exception.FoodAlreadyExistsException;
import com.niit.restaurantservice.exception.FoodItemNotFoundException;
import com.niit.restaurantservice.exception.RestaurantAlreadyExistsException;
import com.niit.restaurantservice.exception.RestaurantNotFoundException;
import com.niit.restaurantservice.service.IRestaurantService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/restaurant")
public class AdminController {

    private final IRestaurantService restaurantService;

    @Autowired
    public AdminController(IRestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }
    @PostMapping("/addFood")
    public ResponseEntity<?> addFoodToRestaurant(@RequestBody Food food, HttpServletRequest httpServletRequest){
        ResponseEntity responseEntity;
        try{
            String emailId=getEmailid(httpServletRequest);
            responseEntity=new ResponseEntity<>(restaurantService.addFoodToRestaurant(food,emailId),HttpStatus.ACCEPTED);
        }
        catch (FoodAlreadyExistsException | RestaurantNotFoundException exception){
            responseEntity=new ResponseEntity<>(exception.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @PutMapping("/updateFood")
    public ResponseEntity<?> updateFoodToRestaurant(HttpServletRequest httpServletRequest,@RequestBody Food food){
        ResponseEntity responseEntity;
        try{
            String emailId=getEmailid(httpServletRequest);
            responseEntity=new ResponseEntity<>(restaurantService.updateFoodToRestaurant(food,emailId),HttpStatus.ACCEPTED);
        }
        catch (FoodAlreadyExistsException exception){
            responseEntity=new ResponseEntity<>(exception.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @GetMapping("/ViewRestaurant")

    public ResponseEntity<?> getRestaurantsByRegisterNo(HttpServletRequest httpServletRequest) {
        String emailId=getEmailid(httpServletRequest);
        return new ResponseEntity<> (restaurantService.getOneRestaurant(emailId),HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/deleteFood/{itemName}")
    public ResponseEntity<?> deleteFood(HttpServletRequest httpServletRequest, @PathVariable String itemName) {
        try {
            String emailId=getEmailid(httpServletRequest);
            restaurantService.deleteFoodToRestaurant(emailId, itemName);
            return new ResponseEntity<>("Food deleted successfully", HttpStatus.OK);
        }  catch (RestaurantNotFoundException e) {
            return new ResponseEntity<>("Restaurant not found", HttpStatus.NOT_FOUND);
        }
    }
    private String getEmailid(HttpServletRequest httpServletRequest){
        String emailId=httpServletRequest.getAttribute("emailId").toString();
        return emailId;
    }
    @GetMapping("/viewOneFood/{foodName}")
    public ResponseEntity<?> getFoodByName(@PathVariable String foodName,HttpServletRequest httpServletRequest) {
        try {
            String emailId=getEmailid(httpServletRequest);
            Food food = restaurantService.getFoodByName(emailId,foodName);
            return new ResponseEntity<>(food, HttpStatus.OK);
        } catch (FoodItemNotFoundException e) {
            return new ResponseEntity<>("Food Not Found", HttpStatus.NOT_FOUND);
        }
    }
}

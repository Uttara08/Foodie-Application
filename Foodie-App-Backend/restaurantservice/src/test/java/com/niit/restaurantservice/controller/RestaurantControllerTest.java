//package com.niit.restaurantservice.controller;
//import com.niit.restaurantservice.domain.Food;
//import com.niit.restaurantservice.domain.Restaurant;
//import com.niit.restaurantservice.exception.RestaurantAlreadyExistsException;
//import com.niit.restaurantservice.service.IRestaurantService;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class RestaurantControllerTest {
//
//    @Mock
//    private IRestaurantService restaurantService;
//
//    @InjectMocks
//    private RestaurantController restaurantController;
//
//    private Food testFood;
//    private Restaurant testRestaurant;
//
//    @BeforeEach
//    void setUp() {
//        testFood = new Food("Food1", "Description1", "food details", 10.0f);
//      //  testRestaurant = new Restaurant("1", "Restaurant1", "KFC", "Location1", Arrays.asList(testFood));
//    }
//
//    @AfterEach
//    void tearDown() {
//        testFood = null;
//        testRestaurant = null;
//    }
//
//    @Test
//    void addRestaurant() {
//        Restaurant mockRestaurant = new Restaurant();
//        when(restaurantService.addRestaurant(any())).thenReturn(mockRestaurant);
//        ResponseEntity<?> responseEntity = restaurantController.addRestaurant(new Restaurant());
//        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
//        assertEquals(mockRestaurant, responseEntity.getBody());
//        verify(restaurantService, times(1)).addRestaurant(any());
//    }
//
//    @Test
//    void addRestaurant_WhenRestaurantAlreadyExists() {
//        when(restaurantService.addRestaurant(any())).thenThrow(new RestaurantAlreadyExistsException("Restaurant already exists"));
//        ResponseEntity<?> responseEntity = restaurantController.addRestaurant(new Restaurant());
//        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
//        assertEquals("Restaurant already exists", responseEntity.getBody());
//        verify(restaurantService, times(1)).addRestaurant(any());
//    }
//
//    @Test
//    void getAllRestaurantSuccess() {
//        List<Restaurant> restaurantList = Arrays.asList(new Restaurant(), new Restaurant());
//        when(restaurantService.getAllRestaurant()).thenReturn(restaurantList);
//
//        ResponseEntity<?> responseEntity = restaurantController.getAllRestaurant();
//
//        assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
//        assertEquals(restaurantList, responseEntity.getBody());
//    }
//
//    @Test
//    void getAllRestaurantNotFound() {
//        when(restaurantService.getAllRestaurant()).thenReturn(Arrays.asList());
//
//        ResponseEntity<?> responseEntity = restaurantController.getAllRestaurant();
//
//        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
//        assertEquals("No Restaurant Present", responseEntity.getBody());
//    }
//}
//
//

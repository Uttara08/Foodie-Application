//package com.niit.restaurantservice.service;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import static org.junit.jupiter.api.Assertions.*;
//import com.niit.restaurantservice.domain.Food;
//import com.niit.restaurantservice.domain.Restaurant;
//import com.niit.restaurantservice.exception.RestaurantNotFoundException;
//import com.niit.restaurantservice.repository.IRestaurantRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class RestaurantServiceImplTest {
//
//    @Mock
//    private IRestaurantRepository restaurantRepository;
//    @InjectMocks
//    private RestaurantServiceImpl restaurantService;
//    private Restaurant testRestaurant;
//    private Food testFood;
//
//    @BeforeEach
////    void setUp() {
////        testFood = new Food("Food1","Idli", "Description1", 10.0f);
////        testRestaurant = new Restaurant("1", "Restaurant1", "KFC","admin","","Location1", Arrays.asList(testFood));
////    }
//
//    @AfterEach
//    void tearDown() {
//        testRestaurant = null;
//        testFood = null;
//    }
//
//    @Test
//    void getAllFoodSuccess() {
//        when(restaurantRepository.findById("1")).thenReturn(Optional.of(testRestaurant));
//            List<Food> foodList = restaurantService.getAllFood("1");
//            assertNotNull(foodList);
//            assertEquals(1, foodList.size());
//            assertEquals(testFood, foodList.get(0));
//    }
//
//    @Test
//        void gestGetAllFoodRestaurantNotFound() {
//            when(restaurantRepository.findById("1")).thenReturn(Optional.empty());
//
//            assertThrows(RestaurantNotFoundException.class, () -> {
//                restaurantService.getAllFood("1");
//            });
//        }
//}
//
//

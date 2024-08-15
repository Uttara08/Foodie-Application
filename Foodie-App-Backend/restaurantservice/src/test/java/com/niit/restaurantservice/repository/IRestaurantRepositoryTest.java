//package com.niit.restaurantservice.repository;
//import com.niit.restaurantservice.domain.Restaurant;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//@ExtendWith(SpringExtension.class)
//@DataMongoTest
//class IRestaurantRepositoryTest {
//
//    @Autowired
//    private IRestaurantRepository restaurantRepository;
//
//    private Restaurant testRestaurant1;
//    private Restaurant testRestaurant2;
//
//    @BeforeEach
////    void setUp() {
////        testRestaurant1 = new Restaurant("1", "Restaurant1", "KFC","admin","", "Location1", null);
////        testRestaurant2 = new Restaurant("2", "Restaurant2", "McDonald's","admin", "","Location2", null);
////        restaurantRepository.saveAll(List.of(testRestaurant1, testRestaurant2));
////    }
//
//    @AfterEach
//    void tearDown() {
//        restaurantRepository.deleteAll();
//    }
//
//    @Test
//    void getAllitemName() {
//        List<Restaurant> restaurantsWithItemName = restaurantRepository.getAllitemName("ItemName");
//        assertEquals(0, restaurantsWithItemName.size());
//    }
//
//
//}

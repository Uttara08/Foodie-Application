package com.niit.restaurantservice.repository;

import com.niit.restaurantservice.domain.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRestaurantRepository extends MongoRepository<Restaurant, String> {
    @Query("{'foodList.itemName':?0}")
    public List<Restaurant> getAllitemName(String itemName);
    public Restaurant findByFoodListFoodId(String foodId);

     public Optional<Restaurant> findByName(String name);
}


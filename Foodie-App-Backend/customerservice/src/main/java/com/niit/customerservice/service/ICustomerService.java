package com.niit.customerservice.service;

import com.niit.customerservice.domain.Customer;
import com.niit.customerservice.domain.Food;
import com.niit.customerservice.exception.CustomerNotFoundException;
import com.niit.customerservice.exception.FavAlreadyExistException;
import com.niit.customerservice.exception.FavoriteException;

import java.util.List;

public interface ICustomerService {
    public Customer registerCustomer(Customer customer);
    public Customer addCustomerFavToList(Food food, String emailId) throws CustomerNotFoundException , FavAlreadyExistException;
    public List<Food> viewFavoriteList(String restaurantId) throws FavoriteException;

    Customer deleteFoodFromFavorite(String emailId, String itemName) throws FavoriteException;

}

package com.niit.customerservice.service;

import com.niit.customerservice.domain.Customer;
import com.niit.customerservice.domain.Food;
import com.niit.customerservice.exception.CustomerAlreadyExistsException;
import com.niit.customerservice.exception.CustomerNotFoundException;
import com.niit.customerservice.exception.FavAlreadyExistException;
import com.niit.customerservice.exception.FavoriteException;
import com.niit.customerservice.proxy.CustomerProxy;
import com.niit.customerservice.repository.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerServiceImpl implements ICustomerService{
    private final ICustomerRepository customerRepository;
    private final CustomerProxy customerProxy;
@Autowired
    public CustomerServiceImpl(ICustomerRepository customerRepository, CustomerProxy customerProxy) {
        this.customerRepository = customerRepository;
        this.customerProxy = customerProxy;
    }

    @Override
    public Customer registerCustomer(Customer customer){
        if(customerRepository.findById(customer.getEmailId()).isPresent()){
            throw new CustomerAlreadyExistsException(" Customer in the given EmailId exists");
        }
        else{
            Customer insertedCustomer=customerRepository.save(customer);
            customerProxy.addCustomer(insertedCustomer);
            return insertedCustomer;
        }
    }

@Override
public Customer addCustomerFavToList(Food food, String emailId) throws CustomerNotFoundException, FavAlreadyExistException {
    Optional<Customer> optionalCustomer = customerRepository.findById(emailId);
    if (optionalCustomer.isPresent()) {
        Customer customer = optionalCustomer.get();
        List<Food> favList = customer.getFavList();
        if (favList == null) {
            favList = new ArrayList<>();
        }

        boolean isAlreadyAdded = favList.stream()
                .anyMatch(f -> Objects.equals(f.getItemName(), food.getItemName()));

        if (isAlreadyAdded) {
            throw new FavAlreadyExistException("This Food is already added to the favorite list");
        } else {
            favList.add(food);
            customer.setFavList(favList);
            customerRepository.save(customer);
            return customer;
        }
    }
    throw new CustomerNotFoundException("Customer Not Found");
}
    @Override
    public List<Food> viewFavoriteList(String restaurantId) throws FavoriteException {
        Optional<Customer> restaurantOptional = customerRepository.findById(restaurantId);
        if (restaurantOptional.isPresent()) {
            Customer customer = restaurantOptional.get();
            List<Food> foodList = customer.getFavList();
            if (foodList == null)
                throw new FavoriteException("No FoodItem added to the list");
            else
                return foodList;
        } else
            throw new CustomerAlreadyExistsException("Customer with given restaurantId is not found");
    }
    @Override
    public Customer deleteFoodFromFavorite(String emailId, String itemName) throws FavoriteException {
        Optional<Customer> restaurantOptional = customerRepository.findById(emailId);
        if (restaurantOptional.isPresent()) {
            Customer customerDetails = restaurantOptional.get();
            List<Food> dishList = customerDetails.getFavList();
            if (dishList == null) {
                throw new FavoriteException("No Food assigned to this Customer");
            } else {
                boolean dishFound = false;
                for (Iterator<Food> iterator = dishList.iterator();
                     iterator.hasNext();) {
                    Food dish = iterator.next();
                    if (dish.getItemName().equals(itemName)) {
                        iterator.remove();
                        dishFound = true;
                        break;
                    }
                }
                if (!dishFound) {
                    throw new FavoriteException("Dish with ID not found for this Customer");
                }
            }
            return customerRepository.save(customerDetails);
        } else {
            throw new CustomerAlreadyExistsException("Customer not found ");
        }
    }

}


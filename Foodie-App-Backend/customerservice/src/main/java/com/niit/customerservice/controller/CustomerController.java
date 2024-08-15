package com.niit.customerservice.controller;

import com.niit.customerservice.domain.Customer;
import com.niit.customerservice.domain.Food;
import com.niit.customerservice.exception.CustomerAlreadyExistsException;
import com.niit.customerservice.exception.FavoriteException;
import com.niit.customerservice.service.ICustomerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v3")
public class CustomerController {

    private final ICustomerService customerService;
@Autowired
    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerCustomer(@RequestBody Customer customer){
        ResponseEntity responseEntity;
        try {
            Customer addedCustomer=customerService.registerCustomer(customer);
            responseEntity=new ResponseEntity<>(addedCustomer, HttpStatus.ACCEPTED);
        }
        catch (CustomerAlreadyExistsException exception){
            responseEntity=new ResponseEntity<>(exception.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }
    @PostMapping("/customer/addFavorite")
    public ResponseEntity<?> addCustomerFavToList(@RequestBody Food food, HttpServletRequest httpServletRequest) {

        ResponseEntity responseEntity;
        try {
            String emailId=getEmailid(httpServletRequest);
            Customer customer =customerService.addCustomerFavToList(food, emailId);
            responseEntity= new ResponseEntity<>(customer,HttpStatus.ACCEPTED);
        } catch (Exception e) {
            responseEntity= new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
        return responseEntity;
    }



    @GetMapping("/customer/viewFavorite")
    public ResponseEntity<?> viewFavoriteList(HttpServletRequest httpServletRequest) throws FavoriteException {
        String emailId=getEmailid(httpServletRequest);
        return new ResponseEntity<>(customerService.viewFavoriteList(emailId), HttpStatus.OK);
    }


    @DeleteMapping("/customer/deleteFood/{itemName}")
    public ResponseEntity<?> deleteFoodFromFavorite(HttpServletRequest httpServletRequest, @PathVariable String itemName) {
        ResponseEntity responseEntity;
        try {
            String emailId=getEmailid(httpServletRequest);
            customerService.deleteFoodFromFavorite(emailId, itemName);
            responseEntity= new ResponseEntity<>("Food deleted successfully", HttpStatus.OK);
        }  catch (FavoriteException exception) {
            responseEntity=new ResponseEntity<>(exception.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }


    private String getEmailid(HttpServletRequest httpServletRequest){
    String emailId=httpServletRequest.getAttribute("emailId").toString();
    return emailId;
}
}

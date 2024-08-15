package com.niit.customerauthservice.controller;

import com.niit.customerauthservice.domain.Customer;
import com.niit.customerauthservice.service.ICustomerService;
import com.niit.customerauthservice.service.SecurityTokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")

public class CustomerController {
    private final ICustomerService customerService;
    private final SecurityTokenGenerator token;
    @Autowired
    public CustomerController(ICustomerService customerService, SecurityTokenGenerator token) {
        this.customerService = customerService;
        this.token = token;
    }
    @PostMapping("/Register")
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer){
        ResponseEntity responseEntity;
        try {
            System.out.println(customer);
            Customer addCustomer = customerService.addCustomer(customer);

            responseEntity=new ResponseEntity<>(addCustomer, HttpStatus.ACCEPTED);
        }
        catch (RuntimeException exception){
            responseEntity=new ResponseEntity<>(exception.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginCustomer(@RequestBody Customer customer){
        ResponseEntity responseEntity;
        try {
            Customer fetchedCustomer = customerService.loginCustomer(customer);
            Map<String,String> map=token.generateToken(fetchedCustomer);
            String role= fetchedCustomer.getRole();
            map.put("role",role);
            responseEntity=new ResponseEntity<>(map, HttpStatus.OK);
        }
        catch (RuntimeException exception){
            responseEntity=new ResponseEntity<>(exception.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }



}
package com.niit.customerauthservice.service;

import com.niit.customerauthservice.domain.Customer;

import java.util.Map;

public interface SecurityTokenGenerator {
    public Map<String,String> generateToken(Customer customer);
}

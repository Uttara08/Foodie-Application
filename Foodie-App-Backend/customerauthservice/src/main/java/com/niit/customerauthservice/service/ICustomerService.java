package com.niit.customerauthservice.service;

import com.niit.customerauthservice.domain.Customer;

public interface ICustomerService {
    public Customer addCustomer(Customer customer);
    public Customer loginCustomer(Customer customer);

}

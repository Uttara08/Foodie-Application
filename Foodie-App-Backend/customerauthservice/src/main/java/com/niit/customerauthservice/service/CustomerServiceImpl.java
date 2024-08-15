package com.niit.customerauthservice.service;

import com.niit.customerauthservice.domain.Customer;
import com.niit.customerauthservice.exception.CustomerAlreadyExistException;
import com.niit.customerauthservice.exception.CustomerLoginException;
import com.niit.customerauthservice.repository.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl  implements ICustomerService{
    private final ICustomerRepository customerRepository;
    @Autowired
    public CustomerServiceImpl(ICustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public Customer addCustomer(Customer customer) throws CustomerAlreadyExistException {

             if (customerRepository.findById(customer.getEmailId()).isPresent())
             throw new CustomerAlreadyExistException("Customer is already present in this emailID");
             else{
            return customerRepository.save(customer);
        }
    }

    @Override
    public Customer loginCustomer(Customer customer) {
        Optional<Customer> customerOptional=customerRepository.findByEmailIdAndPassword(customer.getEmailId(),customer.getPassword());
        if(customerOptional.isPresent()){
            return customerOptional.get();
        }
        else {
            throw new CustomerLoginException("UserName or Password Incorrect");
        }

    }
}

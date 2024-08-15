package com.niit.customerauthservice.repository;

import com.niit.customerauthservice.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICustomerRepository  extends JpaRepository<Customer,String> {
    public Optional<Customer> findByEmailIdAndPassword(String emailId, String password);
}

package com.niit.customerauthservice.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.niit.customerauthservice.domain.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ICustomerRepositoryTest {
    @Autowired
    private ICustomerRepository customerRepository;
    private Customer customer1, customer2;


    @BeforeEach
    void setUp() {
        customer1 = new Customer(" customer1@gmail.com", " Customer1","user");
        customer2 = new Customer(" customer2@gmail.com", " Customer2","user");
    }

    @AfterEach
    void tearDown() {
        customer1 = null;
        customer2 = null;
    }

    @Test
    public void registerCustomerSuccess() {
        Customer addedUser = customerRepository.save(customer1);
        assertEquals(customer1.getEmailId(), addedUser.getEmailId());
    }

    @Test
    public void registerCustomerFailure() {
        Customer addedUser = customerRepository.save(customer1);
        assertNotEquals("wrongemail@example.com", addedUser.getEmailId());
    }
}
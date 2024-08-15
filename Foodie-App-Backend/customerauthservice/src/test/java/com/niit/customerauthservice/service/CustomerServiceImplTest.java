package com.niit.customerauthservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.niit.customerauthservice.domain.Customer;
import com.niit.customerauthservice.exception.CustomerAlreadyExistException;
import com.niit.customerauthservice.exception.CustomerLoginException;
import com.niit.customerauthservice.repository.ICustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
    class CustomerServiceImplTest {

        @Mock
        private ICustomerRepository customerRepository;

        @InjectMocks
        private CustomerServiceImpl customerServiceImpl;
        private Customer customer1, customer2;


        @BeforeEach
        void setUp() {
            customer1 = new Customer("customer1@gmail.com", "Customer1","user");
            customer2 = new Customer(" customer2@gmail.com", " Customer2","user");
        }

        @AfterEach
        void tearDown() {
            customer1 = null;
            customer2 = null;
        }

        @Test
        void testAddCustomerSuccess() throws CustomerAlreadyExistException {
            when(customerRepository.findById("customer1@gmail.com")).thenReturn(Optional.empty());
            when(customerRepository.save(customer1)).thenReturn(customer1);

            Customer addedCustomer = customerServiceImpl.addCustomer(customer1);

            assertEquals(customer1, addedCustomer);
            verify(customerRepository, times(1)).findById("customer1@gmail.com");
            verify(customerRepository, times(1)).save(customer1);
        }

        @Test
        void testAddCustomerFailure() {
            when(customerRepository.findById("customer1@gmail.com")).thenReturn(Optional.of(customer1));

            assertThrows(CustomerAlreadyExistException.class, () -> {
            customerServiceImpl.addCustomer(customer1);
        });

            verify(customerRepository, times(1)).findById("customer1@gmail.com");
            verify(customerRepository, never()).save(any());
    }

    @Test
    void testLoginCustomerSuccess() throws CustomerLoginException {
            Customer customer1 = new Customer("customer1@gmail.com", "customer1","user");
            when(customerRepository.findByEmailIdAndPassword("customer1@gmail.com", "customer1"))
                .thenReturn(Optional.of(customer1));

            Customer loggedInCustomer = customerServiceImpl.loginCustomer(customer1);

            assertEquals(customer1, loggedInCustomer);
            verify(customerRepository, times(1)).findByEmailIdAndPassword("customer1@gmail.com", "customer1");
    }

    @Test
        void testLoginUserFailure() {
            when(customerRepository.findByEmailIdAndPassword("customer1@gmail.com", "customer1")).thenReturn(Optional.empty());

            Customer customer = new Customer("customer1@gmail.com", "customer1","user");
            assertThrows(CustomerLoginException.class, () -> {
                customerServiceImpl.loginCustomer(customer);
            });
        }
    }
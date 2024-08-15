package com.niit.customerservice.proxy;

import com.niit.customerservice.domain.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
@FeignClient(name="customerauthservice",url="http://localhost:8084")
public interface CustomerProxy {
        @PostMapping("/api/v1/Register")
        public ResponseEntity<?> addCustomer(@RequestBody Customer customer);
}


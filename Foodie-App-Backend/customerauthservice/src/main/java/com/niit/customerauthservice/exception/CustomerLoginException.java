package com.niit.customerauthservice.exception;

public class CustomerLoginException extends RuntimeException {
    public CustomerLoginException(String message) {
        super(message);
    }
}

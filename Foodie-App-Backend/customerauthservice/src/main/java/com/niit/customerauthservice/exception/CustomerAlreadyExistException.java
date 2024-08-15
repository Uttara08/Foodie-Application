package com.niit.customerauthservice.exception;

public class CustomerAlreadyExistException extends RuntimeException{
    public CustomerAlreadyExistException(String message){
        super(message);
    }
}

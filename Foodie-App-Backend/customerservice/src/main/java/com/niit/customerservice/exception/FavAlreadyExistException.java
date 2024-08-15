package com.niit.customerservice.exception;

public class FavAlreadyExistException extends RuntimeException {
    public FavAlreadyExistException(String message) {
        super(message);
    }
}

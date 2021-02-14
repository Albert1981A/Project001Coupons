package com.AlbertAbuav.exceptions;

public class invalidCustomerException extends Exception{
    public invalidCustomerException(String message) {
        super("This is an invalid Customer operation: " + message);
    }
}

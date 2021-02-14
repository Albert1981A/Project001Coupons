package com.AlbertAbuav.exceptions;

public class invalidCompanyException extends Exception{
    public invalidCompanyException(String message) {
        super("This is an invalid Company operation: " + message);
    }
}

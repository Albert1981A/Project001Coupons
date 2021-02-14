package com.AlbertAbuav.exceptions;

public class invalidAdminException extends Exception{
    public invalidAdminException(String message) {
        super("This is an invalid Administrator operation: " + message);
    }
}

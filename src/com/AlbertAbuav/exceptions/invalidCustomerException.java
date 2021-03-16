package com.AlbertAbuav.exceptions;

import com.AlbertAbuav.utils.Colors;

public class invalidCustomerException extends Exception{
    public invalidCustomerException(String message) {
        super(Colors.RED + "This is an invalid Customer operation: " + message + Colors.RESET);
    }
}

package com.AlbertAbuav.exceptions;

import com.AlbertAbuav.utils.Colors;

public class invalidCompanyException extends Exception{
    public invalidCompanyException(String message) {
        super(Colors.RED + "This is an invalid Company operation: " + message + Colors.RESET);
    }
}

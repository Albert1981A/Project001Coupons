package com.AlbertAbuav.exceptions;

import com.AlbertAbuav.utils.Colors;

public class invalidAdminException extends Exception{
    public invalidAdminException(String message) {
        super(Colors.RED + "This is an invalid Administrator operation: " + message + Colors.RESET);
    }
}

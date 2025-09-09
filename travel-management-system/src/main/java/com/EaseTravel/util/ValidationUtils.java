package com.EaseTravel.util;

public class ValidationUtils {
    public static boolean isEmailValid(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }
}

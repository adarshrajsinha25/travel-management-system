package com.EaseTravel.travel_management_system.util;

public class EmailTemplate {
    public static String welcomeEmail(String username) {
        return "Welcome, " + username + "! Thank you for registering with EaseTravel.";
    }
}

package org.example.travelexpertsphase3desktop.Utils;
import java.util.regex.Pattern;
public class Validator {
    //  Check if a field is not empty
    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    //  Check if a value contains only letters (for names, cities, countries)
    public static boolean isAlpha(String value) {
        return value.matches("^[a-zA-Z\\s]+$");
    }

    // Check if email is in a valid format
    public static boolean isValidEmail(String email) {
        return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }


    //  Check if a value is a valid province code (e.g., AB, ON)
    public static boolean isProvinceCode(String value) {
        return value.matches("^[A-Z]{2}$"); // Ensures exactly 2 uppercase letters
    }

    // Check if a postal code is valid (Canada: A1A 1A1, US: 12345 or 12345-6789)
    public static boolean isPostalCode(String value) {
        return value.matches("^[A-Za-z]\\d[A-Za-z] ?\\d[A-Za-z]\\d$") || // Canada
                value.matches("^\\d{5}(-\\d{4})?$"); // USA
    }

    // Check if a phone number is valid (formats like 123-456-7890, (123) 456-7890)
    public static boolean isPhoneNumber(String value) {
        return value.matches("^\\(\\d{3}\\) \\d{3}-\\d{4}$") || // (123) 456-7890
                value.matches("^\\d{3}-\\d{3}-\\d{4}$") || // 123-456-7890
                value.matches("^\\d{10}$"); // 1234567890
    }

    //  Check if an integer value is valid (e.g., agency ID)
    public static boolean isValidInteger(String value) {
        return value.matches("\\d+");
    }

}

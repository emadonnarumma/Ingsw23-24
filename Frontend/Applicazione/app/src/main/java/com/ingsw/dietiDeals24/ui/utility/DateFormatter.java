package com.ingsw.dietiDeals24.ui.utility;

public class DateFormatter {
    public static String format(String date) {
        date.substring(0, 9);
        String[] dateParts = date.replace("/", "-").split("-");
        return dateParts[2] + "-" + dateParts[1] + "-" + dateParts[0];
    }
}

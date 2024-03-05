package com.ingsw.dietiDeals24.ui.utility;

import java.text.NumberFormat;
import java.util.Locale;

public class NumberFormatter {

    private NumberFormatter() {}

    public static String formatPrice(Double price) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.ITALY);
        return numberFormat.format(price) + "€";
    }
}

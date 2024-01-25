package com.ingsw.dietiDeals24.ui.utility;

import android.text.InputFilter;
import android.text.Spanned;

public class DecimalInputFilter implements InputFilter {

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        for (int i = start; i < end; i++) {
            if (!Character.isDigit(source.charAt(i)) && source.charAt(i) != '.' && source.charAt(i) != '€') {
                return "";
            }
            if (source.charAt(i) == '.' && dest.toString().contains(".")) {
                return "";
            }
            if (dest.toString().contains(".") && dend > dest.toString().indexOf(".") + 2) {
                return "";
            }
        }

        if (dest.toString().isEmpty() && source.toString().isEmpty()) {
            return "0,0€";
        } else if (dest.toString().equals("0,0€") && !source.toString().isEmpty()) {
            return source.toString() + "€";
        } else if (!dest.toString().endsWith("€")) {
            return source.toString() + "€";
        }

        return null;
    }
}
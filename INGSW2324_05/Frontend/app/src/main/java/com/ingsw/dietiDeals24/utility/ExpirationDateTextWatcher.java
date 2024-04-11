package com.ingsw.dietiDeals24.utility;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class ExpirationDateTextWatcher implements TextWatcher {
    private static final int TOTAL_SYMBOLS = 7;
    private static final int TOTAL_DIGITS = 6;
    private static final int DIVIDER_MODULO = 2;
    private static final char DIVIDER = '/';

    private EditText editText;

    public ExpirationDateTextWatcher(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (before > count) {
            return;
        }
        if (!isInputCorrect(s, TOTAL_SYMBOLS, DIVIDER_MODULO, DIVIDER)) {
            fixInput(s, start, before, count);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {}

    private boolean isInputCorrect(CharSequence s, int totalSymbols, int dividerModulo, char divider) {
        boolean isCorrect = s.length() <= totalSymbols;
        for (int i = 0; i < s.length(); i++) {
            isCorrect &= (i > 0 && (i + 1) % dividerModulo == 0) ? isDivider(s.charAt(i), divider) : isDigit(s.charAt(i));
        }
        return isCorrect;
    }

    private boolean isDigit(char c) {
        try {
            Integer.parseInt(String.valueOf(c));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isDivider(char c, char divider) {
        return c == divider;
    }

    private String buildCorrectString(CharSequence s, int start, int before, int count) {
        StringBuilder sb = new StringBuilder();
        int alreadyPlacedDividers = 0;
        for (int i = 0; i < s.length(); i++) {
            if (sb.length() < TOTAL_SYMBOLS) {
                if (alreadyPlacedDividers < TOTAL_DIGITS / 2) {
                    sb.append(s.charAt(i));
                    if (isDigit(s.charAt(i)) && (i > 0) && (i + 1) % DIVIDER_MODULO == 0 && alreadyPlacedDividers < 1) {
                        sb.append(DIVIDER);
                        alreadyPlacedDividers++;
                    }
                }
            }
        }
        return sb.toString();
    }

    private void fixInput(CharSequence s, int start, int before, int count) {
        String input = s.toString();
        String cleanedInput = deleteNonDigits(input);
        String correctlyDividedInput = buildCorrectString(cleanedInput, start, before, count);
        editText.removeTextChangedListener(this);
        editText.setText(correctlyDividedInput);
        handleSelection();
        editText.addTextChangedListener(this);
    }

    private String deleteNonDigits(CharSequence s) {
        return s.toString().replaceAll("\\D", "");
    }

    private void handleSelection() {
        if (editText.getText().length() <= TOTAL_SYMBOLS) {
            editText.setSelection(editText.getText().length());
        } else {
            editText.setSelection(TOTAL_SYMBOLS);
        }
    }
}
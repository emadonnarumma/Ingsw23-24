package com.ingsw.dietiDeals24.ui.utility;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class FourDigitCardFormatWatcher implements TextWatcher {
    private EditText editText;

    public FourDigitCardFormatWatcher(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}


    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}


    @Override
    public void afterTextChanged(Editable s) {
        String input = s.toString();
        String soloNumeri = input.replaceAll("\\D", "");

        if (soloNumeri.length() % 4 == 0 && input.endsWith("-")) {
            return;
        }

        StringBuilder inputFormattato = new StringBuilder();
        for (int i = 0; i < soloNumeri.length(); i++) {
            if (i % 4 == 0 && i != 0 && i != soloNumeri.length() - 1) {
                inputFormattato.append("-");
            }
            inputFormattato.append(soloNumeri.charAt(i));
        }

        editText.removeTextChangedListener(this);
        editText.setText(inputFormattato.toString());
        if (inputFormattato.length() <= editText.getText().length()) {
            editText.setSelection(inputFormattato.length());
        }
        editText.addTextChangedListener(this);
    }
}
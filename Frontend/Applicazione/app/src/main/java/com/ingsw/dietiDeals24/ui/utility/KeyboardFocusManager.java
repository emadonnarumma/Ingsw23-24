package com.ingsw.dietiDeals24.ui.utility;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class KeyboardFocusManager {
    private Fragment fragment;
    private Activity activity;
    private View view;

    public KeyboardFocusManager(Fragment fragment, View view) {
        this.fragment = fragment;
        this.view = view;
    }

    public KeyboardFocusManager(Activity activity, View view) {
        this.activity = activity;
        this.view = view;
    }

    private Activity getActivity() {
        return fragment != null ? fragment.requireActivity() : activity;
    }

    public void closeKeyboardWhenUserClickOutside() {
        view.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (fragment == null || fragment.isAdded()) {
                    View focusView = getActivity().getCurrentFocus();
                    if (focusView instanceof EditText) {
                        Rect outRect = new Rect();
                        focusView.getGlobalVisibleRect(outRect);
                        if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
                        }
                    }
                    v.performClick();
                }
            }
            return false;
        });
    }

    public void loseFocusWhenKeyboardClose() {
        view.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            if (fragment == null || fragment.isAdded()) {
                Rect r = new Rect();
                view.getWindowVisibleDisplayFrame(r);
                int screenHeight = view.getRootView().getHeight();

                int keypadHeight = screenHeight - r.bottom;

                boolean isKeyboardNowVisible = keypadHeight > screenHeight * 0.15;

                if (!isKeyboardNowVisible) {
                    View focusView = getActivity().getCurrentFocus();
                    if (focusView instanceof EditText) {
                        focusView.clearFocus();
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
                    }
                }
            }
        });
    }
}
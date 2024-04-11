package com.ingsw.dietiDeals24.utility;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.StringRes;

public class ToastManager {
    private static Toast mToast;

    public static void showToast(Context context, String message) {
        if (mToast != null) {
            mToast.cancel();
        }

        mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        mToast.show();
    }

    public static void showToast(Context context, @StringRes int resId) {
        if (mToast != null) {
            mToast.cancel();
        }

        mToast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
        mToast.show();
    }
}


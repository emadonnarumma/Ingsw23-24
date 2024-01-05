package com.ingsw.dietiDeals24.activity.utility;

import android.content.Context;
import android.widget.Toast;

public class ToastManager {
    private static Toast mToast;

    public static void showToast(Context context, String message) {
        if (mToast != null) {
            mToast.cancel();
        }

        mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        mToast.show();
    }
}


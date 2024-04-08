package com.ingsw.dietiDeals24.utility;

import android.content.Context;
import android.content.Intent;

import com.ingsw.dietiDeals24.ui.home.HomeActivity;

public interface OnNavigateToHomeActivityFragmentListener {
   static void navigateTo(String fragmentName, Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra("openFragment", fragmentName);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}

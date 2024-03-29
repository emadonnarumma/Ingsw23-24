package com.ingsw.dietiDeals24.ui.utility;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.droidnet.DroidListener;
import com.droidnet.DroidNet;

public abstract class
CheckConnectionActivity extends AppCompatActivity implements DroidListener {

    private DroidNet mDroidNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDroidNet = DroidNet.getInstance();
        mDroidNet.addInternetConnectivityListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDroidNet.removeInternetConnectivityChangeListener(this);
    }

    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {

        if (!isConnected) {
            PopupGeneratorOf.connectionLostPopup(this);
        }
    }
}

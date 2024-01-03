package com.ingsw.dietiDeals24.activity.ui.myAuctions;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyAuctionViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MyAuctionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is myAuction fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
package com.ingsw.dietiDeals24.activity.ui.home.createAuction;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CreateAuctionViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CreateAuctionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
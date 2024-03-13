package com.ingsw.dietiDeals24.ui.home.createAuction.fragments.generalAuctionAttributes;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ingsw.dietiDeals24.model.enumeration.Category;
import com.ingsw.dietiDeals24.model.enumeration.Wear;
import com.ingsw.dietiDeals24.ui.home.createAuction.auctionHolder.AuctionHolder;

import java.util.ArrayList;

public class GeneralAuctionAttributesViewModel extends ViewModel {
    private MutableLiveData<AuctionHolder> newAuction = new MutableLiveData<>();

    private static class SingletonHolder {
        private static final GeneralAuctionAttributesViewModel INSTANCE = new GeneralAuctionAttributesViewModel();
    }

    private GeneralAuctionAttributesViewModel() {}

    public static GeneralAuctionAttributesViewModel getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public MutableLiveData<AuctionHolder> getNewAuction() {
        return newAuction;
    }

    public void setNewAuction(MutableLiveData<AuctionHolder> newAuction) {
        this.newAuction = newAuction;
    }

    public void generalAuctionAttributesChanged(String title,
                                                String description,
                                                Wear wear, Category category, ArrayList<Uri> images) {

        newAuction.postValue(new AuctionHolder(title, images, description, wear, category));
    }
}
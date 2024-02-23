package com.ingsw.dietiDeals24.ui.home.createAuction.fragments.generalAuctionAttributes;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ingsw.dietiDeals24.model.enumeration.Category;
import com.ingsw.dietiDeals24.model.enumeration.Wear;
import com.ingsw.dietiDeals24.ui.home.createAuction.auctionHolder.AuctionHolder;

import java.util.ArrayList;
import java.util.List;

public class GeneralAuctionAttributesViewModel extends ViewModel {
    private static GeneralAuctionAttributesViewModel instance;
    private MutableLiveData<AuctionHolder> newAuction = new MutableLiveData<>();

    private static class GeneralAuctionViewModelHolder {
        private static final GeneralAuctionAttributesViewModel INSTANCE = new GeneralAuctionAttributesViewModel();
    }

    private GeneralAuctionAttributesViewModel() {}

    public static synchronized GeneralAuctionAttributesViewModel getInstance() {
        return GeneralAuctionViewModelHolder.INSTANCE;
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
package com.ingsw.dietiDeals24.ui.home.createAuction.fragments.generalAuctionAttributes;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ingsw.dietiDeals24.enumeration.Category;
import com.ingsw.dietiDeals24.enumeration.Wear;
import com.ingsw.dietiDeals24.ui.utility.auctionHolder.AuctionHolder;

import java.util.List;

public class GeneralAuctionAttributesViewModel extends ViewModel {
    private MutableLiveData<AuctionHolder> newAuction = new MutableLiveData<>();
    private List<Uri> images;


    public MutableLiveData<AuctionHolder> getNewAuction() {
        return newAuction;
    }

    public void generalAuctionAttributesChanged(String title,
                                                String description,
                                                Wear wear, Category category) {

        newAuction.postValue(new AuctionHolder(title, images, description, wear, category));
    }

    public void setImages(List<Uri> images) {

        this.images = images;
    }

}
package com.ingsw.dietiDeals24.ui.home.createAuction.fragments.generalAuctionAttributes;

import android.content.Context;
import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ingsw.dietiDeals24.enumeration.Category;
import com.ingsw.dietiDeals24.enumeration.Wear;
import com.ingsw.dietiDeals24.model.Auction;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GeneralAuctionAttributesViewModel extends ViewModel {
    private MutableLiveData<Auction> newAuction = new MutableLiveData<>();
    private List<Uri> images;


    public MutableLiveData<Auction> getNewAuction() {
        return newAuction;
    }

    public void generalAuctionAttributesChanged(String title,
                                                String description,
                                                Wear wear, Category category) {

        newAuction.postValue(new Auction(title, images, description, wear, category));
    }

    public void setImages(List<Uri> images) {

        this.images = images;
    }

}
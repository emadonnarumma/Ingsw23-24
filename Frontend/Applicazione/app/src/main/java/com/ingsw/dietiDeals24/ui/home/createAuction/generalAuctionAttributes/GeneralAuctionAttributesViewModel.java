package com.ingsw.dietiDeals24.ui.home.createAuction.generalAuctionAttributes;

import android.content.Context;
import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.AuctionHolder;
import com.ingsw.dietiDeals24.controller.formstate.GeneralAuctionAttributesFormState;
import com.ingsw.dietiDeals24.model.enumeration.Category;
import com.ingsw.dietiDeals24.model.enumeration.Wear;

import java.util.ArrayList;

public class GeneralAuctionAttributesViewModel extends ViewModel {
    private MutableLiveData<AuctionHolder> newAuction = new MutableLiveData<>();
    private MutableLiveData<GeneralAuctionAttributesFormState> generalAuctionAttributesFormState = new MutableLiveData<>();


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


    public LiveData<GeneralAuctionAttributesFormState> getGeneralAuctionAttributesFormState() {
        return generalAuctionAttributesFormState;
    }

    public void generalAuctionInputChanged(String title, String description, boolean isWearSelected, boolean isCategorySelected, Context context) {
        if (!isTitleFormatValid(title)) {
            generalAuctionAttributesFormState.setValue(new GeneralAuctionAttributesFormState(context.getString(R.string.auction_title_error), null, null, null));
        } else if (!isDescriptionFormatValid(description)) {
            generalAuctionAttributesFormState.setValue(new GeneralAuctionAttributesFormState(null, context.getString(R.string.auction_description_error), null, null));
        } else if (!isWearSelected) {
            generalAuctionAttributesFormState.setValue(new GeneralAuctionAttributesFormState(null, null, context.getString(R.string.auction_wear_error), null));
        } else if (!isCategorySelected) {
            generalAuctionAttributesFormState.setValue(new GeneralAuctionAttributesFormState(null, null, null, context.getString(R.string.auction_category_error)));
        } else {
            generalAuctionAttributesFormState.setValue(new GeneralAuctionAttributesFormState(true));
        }
    }

    public boolean isTitleFormatValid(String title) {
        return !title.isEmpty() && title.length() <= 40;
    }

    public boolean isDescriptionFormatValid(String description) {
        return !description.isEmpty() && description.length() <= 500;
    }
}
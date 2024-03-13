package com.ingsw.dietiDeals24.ui.home.searchAuctions;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchAuctionsViewModel extends ViewModel {
    private final MutableLiveData<Integer> spinnerIndex;

    private SearchAuctionsViewModel() {
        spinnerIndex = new MutableLiveData<>();
    }

    public static class SingletonHolder {
        public static final SearchAuctionsViewModel INSTANCE = new SearchAuctionsViewModel();
    }

    public static SearchAuctionsViewModel getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public MutableLiveData<Integer> getSpinnerIndex() {
        return spinnerIndex;
    }

    public void setSpinnerIndex(int index) {
        spinnerIndex.setValue(index);
    }

    public void spinnerIndexChanged(int index) {
        spinnerIndex.postValue(index);
    }
}
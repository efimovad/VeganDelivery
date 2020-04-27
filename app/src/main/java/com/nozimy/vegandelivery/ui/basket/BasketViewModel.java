package com.nozimy.vegandelivery.ui.basket;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class BasketViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;

    public BasketViewModel(Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
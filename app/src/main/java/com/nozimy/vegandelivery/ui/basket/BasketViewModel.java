package com.nozimy.vegandelivery.ui.basket;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.nozimy.vegandelivery.interactors.places.PlacesListInteractor;
import com.nozimy.vegandelivery.interactors.places.PlacesListInteractorDefault;

public class BasketViewModel extends AndroidViewModel {

    private PlacesListInteractor interactor = PlacesListInteractorDefault.getInstance(getApplication());

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
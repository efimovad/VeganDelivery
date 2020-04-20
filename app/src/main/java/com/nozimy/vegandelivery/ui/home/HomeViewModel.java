package com.nozimy.vegandelivery.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nozimy.vegandelivery.db.entity.PlaceEntity;
import com.nozimy.vegandelivery.interactors.places.PlacesListInteractor;

import java.util.List;

import javax.inject.Inject;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

//    private PlacesListInteractor mPlacesListInteractor;

    @Inject
    public HomeViewModel() {
//        this.mPlacesListInteractor = placesListInteractor;
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

//    public LiveData<List<PlaceEntity>> getPlaces(){
//        return this.mPlacesListInteractor.getPlaces();
//    }
}
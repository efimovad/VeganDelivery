package com.nozimy.vegandelivery.ui.place_list;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.nozimy.vegandelivery.db.model.Dish;
import com.nozimy.vegandelivery.db.model.Place;
import com.nozimy.vegandelivery.interactors.DishesInteractor;
import com.nozimy.vegandelivery.interactors.places.PlacesListInteractor;

import java.util.List;

public class PlaceListViewModel extends AndroidViewModel {
    private PlacesListInteractor interactor = new PlacesListInteractor(getApplication());
    private LiveData<List<Place>> mPlaceList = interactor.getPlaces();

    public PlaceListViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Place>> getPlaceList() {
        return mPlaceList;
    }

    public void refresh() {
        interactor.refresh();
    }
}
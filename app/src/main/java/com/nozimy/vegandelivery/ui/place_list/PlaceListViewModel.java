package com.nozimy.vegandelivery.ui.place_list;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.nozimy.vegandelivery.db.entity.PlaceEntity;
import com.nozimy.vegandelivery.db.model.MyPlace;
import com.nozimy.vegandelivery.interactors.places.PlacesListInteractor;

import java.util.List;

public class PlaceListViewModel extends AndroidViewModel {
    private PlacesListInteractor interactor = new PlacesListInteractor(getApplication());
    private LiveData<List<PlaceEntity>> mPlaceList = interactor.getPlaces();
    private LiveData<List<PlaceEntity>> mFavPlaceList = interactor.getFavourite();

    public PlaceListViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<PlaceEntity>> getPlaceList() { return mPlaceList; }
    public LiveData<List<PlaceEntity>> getFavPlaceList() { return mFavPlaceList; }


    public void refresh() {
        interactor.refresh();
    }

    public List<MyPlace> transformPlaceEntityToMyPlace(List<PlaceEntity> list) {
        return PlacesListInteractor.transformPlaceEntityToMyPlace(list);
    }

    public void changeFavStatus(long id, boolean value) {
        interactor.changeFavStatus(id, value);
    }
}

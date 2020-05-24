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

import java.util.ArrayList;
import java.util.List;

public class PlaceListViewModel extends AndroidViewModel {
    private PlacesListInteractor interactor = new PlacesListInteractor(getApplication());
    private LiveData<List<PlaceEntity>> mPlaceList = interactor.getPlaces();

    public PlaceListViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<PlaceEntity>> getPlaceList() {
        return mPlaceList;
    }

    public void refresh() {
        interactor.refresh();
    }

    public List<MyPlace> transformPlaceEntityToMyPlace(List<PlaceEntity> list) {
        return PlacesListInteractor.transformPlaceEntityToMyPlace(list);
    }
}

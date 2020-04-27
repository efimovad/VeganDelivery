package com.nozimy.vegandelivery.interactors.places;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.nozimy.vegandelivery.app.App;
import com.nozimy.vegandelivery.db.DataRepository;
import com.nozimy.vegandelivery.db.model.Place;

import java.util.List;

import javax.inject.Inject;

public class PlacesListInteractorDefault extends PlacesListInteractor {
    @NonNull
    private DataRepository mRepository;

    public PlacesListInteractorDefault(Context context) {
        super(context);
    }

//    @Inject
//    public PlacesListInteractorDefault(@NonNull DataRepository repository){
//        this.mRepository = repository;
//    }

    @Override
    public LiveData<List<Place>> getPlaces() {
        return mRepository.getPlaces();
    }

    @NonNull
    public static PlacesListInteractor getInstance(Context context) {
        return App.from(context).getPlacesInteractor();
    }
}

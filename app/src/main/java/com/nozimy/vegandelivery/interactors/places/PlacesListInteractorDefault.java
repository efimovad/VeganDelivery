package com.nozimy.vegandelivery.interactors.places;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.nozimy.vegandelivery.db.DataRepository;
import com.nozimy.vegandelivery.db.entity.PlaceEntity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class PlacesListInteractorDefault implements PlacesListInteractor {
    @NonNull
    private DataRepository mRepository;

    @Inject
    public PlacesListInteractorDefault(@NonNull DataRepository repository){
        this.mRepository = repository;
    }

    @Override
    public LiveData<List<PlaceEntity>> getPlaces() {
        return mRepository.getPlaces();
    }
}

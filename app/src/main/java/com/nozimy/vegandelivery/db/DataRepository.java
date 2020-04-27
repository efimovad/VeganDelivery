package com.nozimy.vegandelivery.db;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.nozimy.vegandelivery.db.entity.PlaceEntity;
import com.nozimy.vegandelivery.db.model.Place;

import java.util.List;

import javax.inject.Inject;

public class DataRepository {
    @NonNull
    private AppDatabase mDatabase;

    @Inject
    public DataRepository(AppDatabase database) {
        this.mDatabase = database;
    }

    public LiveData<List<Place>> getPlaces(){
        return this.mDatabase.placeDao().getAll();
    }
}

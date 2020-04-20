package com.nozimy.vegandelivery.db;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.nozimy.vegandelivery.db.entity.PlaceEntity;

import java.util.List;

import javax.inject.Inject;

public class DataRepository {
    @NonNull
    private AppDatabase mDatabase;

    @Inject
    public DataRepository(AppDatabase database) {
        this.mDatabase = database;
    }

    public LiveData<List<PlaceEntity>> getPlaces(){
        return this.mDatabase.placeDao().getAll();
    }
}

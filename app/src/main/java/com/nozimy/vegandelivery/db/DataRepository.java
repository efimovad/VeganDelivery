package com.nozimy.vegandelivery.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.nozimy.vegandelivery.db.entity.PlaceEntity;
import com.nozimy.vegandelivery.db.model.Place;

import java.util.List;


public class DataRepository {
    @NonNull
    private AppDatabase mDatabase;

    public DataRepository(Context context) {
        mDatabase = DBHelper.getInstance(context).getAppDb();
    }

    public LiveData<List<PlaceEntity>> getPlaces(){
        return this.mDatabase.placeDao().getAll();
    }
}

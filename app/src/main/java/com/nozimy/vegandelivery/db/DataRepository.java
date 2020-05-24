package com.nozimy.vegandelivery.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.nozimy.vegandelivery.db.entity.PersonEntity;
import com.nozimy.vegandelivery.db.entity.PlaceEntity;

import java.security.spec.ECField;
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

    public LiveData<PersonEntity> getPerson(String personId){
        return this.mDatabase.personDao().getById(personId);
    }

    public void insertPerson(PersonEntity personEntity){
        try {
            this.mDatabase.personDao().insert(personEntity);
        } catch (Exception e){

        }
    }

    public void updatePerson(String id, String name, String phone, String email){
        this.mDatabase.personDao().update(id, name, phone, email);
    }

    public void insertPlace(PlaceEntity placeEntity){
        this.mDatabase.placeDao().insert(placeEntity);
    }
}

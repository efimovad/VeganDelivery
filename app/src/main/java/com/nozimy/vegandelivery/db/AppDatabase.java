package com.nozimy.vegandelivery.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.nozimy.vegandelivery.db.dao.PlaceDao;
import com.nozimy.vegandelivery.db.entity.PlaceEntity;

@Database(entities = {PlaceEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PlaceDao placeDao();
}

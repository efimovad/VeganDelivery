package com.nozimy.vegandelivery.db;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.nozimy.vegandelivery.db.dao.PersonDao;
import com.nozimy.vegandelivery.db.dao.PlaceDao;
import com.nozimy.vegandelivery.db.entity.DishEntity;
import com.nozimy.vegandelivery.db.entity.PersonEntity;
import com.nozimy.vegandelivery.db.entity.PlaceEntity;


@Database(entities = {PlaceEntity.class, PersonEntity.class, DishEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public static String DB_NAME = "vegan-delivery-db";

    public abstract PlaceDao placeDao();
    public abstract PersonDao personDao();
}

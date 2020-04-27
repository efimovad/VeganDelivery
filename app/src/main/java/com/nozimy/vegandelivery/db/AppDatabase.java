package com.nozimy.vegandelivery.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.nozimy.vegandelivery.db.dao.PlaceDao;
import com.nozimy.vegandelivery.db.entity.DishEntity;
import com.nozimy.vegandelivery.db.entity.PersonEntity;
import com.nozimy.vegandelivery.db.entity.PlaceEntity;
import com.nozimy.vegandelivery.db.model.Place;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {PlaceEntity.class, PersonEntity.class, DishEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public static String DB_NAME = "vegan-delivery-db";

    public abstract PlaceDao placeDao();


//    private static volatile AppDatabase INSTANCE;
//    private static final int NUMBER_OF_THREADS = 4;

    // ExecutorService with a fixed thread pool that you will use
    // to run database operations asynchronously on a background thread.
//    static final ExecutorService databaseWriteExecutor =
//            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

//    public static AppDatabase getDatabase(final Context context) {
//        if (INSTANCE == null) {
//            synchronized (AppDatabase.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
//                            AppDatabase.class, DB_NAME)
//                            .build();
//                }
//            }
//        }
//        return INSTANCE;
//    }
}

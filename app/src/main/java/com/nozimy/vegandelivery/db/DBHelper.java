package com.nozimy.vegandelivery.db;

import android.content.Context;

import androidx.room.Room;

public class DBHelper {

    private static DBHelper sInstance;
    private Context mContext;
    private final AppDatabase mDb;


    public DBHelper(Context context) {
        mContext = context;
        mDb = Room.databaseBuilder(context, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build();
    }

    public AppDatabase getAppDb() {
        return mDb;
    }


    public static DBHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DBHelper(context);
        }
        return sInstance;
    }

}

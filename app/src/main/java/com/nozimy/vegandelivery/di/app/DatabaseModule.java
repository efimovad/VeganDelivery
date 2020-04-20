package com.nozimy.vegandelivery.di.app;

import android.content.Context;

import androidx.room.Room;

import com.nozimy.vegandelivery.BuildConfig;
import com.nozimy.vegandelivery.db.AppDatabase;

import javax.inject.Singleton;

import dagger.Provides;

public class DatabaseModule {
    @Provides
    @Singleton
    public AppDatabase provideDatabase(Context context){
        return Room.databaseBuilder(context, AppDatabase.class, "vegan-delivery-db")
                .build();
    }
}

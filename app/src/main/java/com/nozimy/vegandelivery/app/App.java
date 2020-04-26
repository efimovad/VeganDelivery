package com.nozimy.vegandelivery.app;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.nozimy.vegandelivery.db.AppDatabase;
import com.nozimy.vegandelivery.db.DataRepository;
import com.nozimy.vegandelivery.di.app.AppComponent;
import com.nozimy.vegandelivery.di.app.AppModule;
import com.nozimy.vegandelivery.di.app.DaggerAppComponent;
import com.nozimy.vegandelivery.di.app.DatabaseModule;
import com.nozimy.vegandelivery.interactors.places.PlacesListInteractor;
import com.nozimy.vegandelivery.interactors.places.PlacesListInteractorDefault;


public class App extends Application {
    private AppComponent appComponent;

    private AppDatabase mDatabase;
    private DataRepository mDataRepository;
    private PlacesListInteractor mPlacesListInteractor;

    @Override
    public void onCreate() {
        super.onCreate();
        initDependencies();

//        mDatabase = AppDatabase.getDatabase(getApplicationContext());
//        mDataRepository = new DataRepository(mDatabase);
//        mPlacesListInteractor = new PlacesListInteractorDefault(mDataRepository);
    }

    private void initDependencies(){
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this.getBaseContext()))
                .databaseModule(new DatabaseModule())
                .build();
        appComponent.inject(this);
    }

    @NonNull
    public AppComponent getAppComponent() {
        if(appComponent == null){
            initDependencies();
        }
        return appComponent;
    }

    public static App from(Context context) {
        return (App) context.getApplicationContext();
    }

    public PlacesListInteractor getPlacesInteractor() {
        return mPlacesListInteractor;
    }
}

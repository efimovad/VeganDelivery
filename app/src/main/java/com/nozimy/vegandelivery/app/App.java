package com.nozimy.vegandelivery.app;

import android.app.Application;

import androidx.annotation.NonNull;

import com.nozimy.vegandelivery.di.app.AppComponent;
import com.nozimy.vegandelivery.di.app.AppModule;
import com.nozimy.vegandelivery.di.app.DaggerAppComponent;
import com.nozimy.vegandelivery.di.app.DatabaseModule;


public class App extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initDependencies();
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
}

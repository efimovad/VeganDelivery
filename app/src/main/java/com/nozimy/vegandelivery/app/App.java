package com.nozimy.vegandelivery.app;

import android.app.Application;
import android.content.Context;

import com.nozimy.vegandelivery.network.ApiRepo;


public class App extends Application {

    private ApiRepo mApiRepo;

    @Override
    public void onCreate() {
        super.onCreate();

        mApiRepo = new ApiRepo();
    }

    public static App from(Context context) {
        return (App) context.getApplicationContext();
    }

    public ApiRepo getApis() {
        return mApiRepo;
    }
}

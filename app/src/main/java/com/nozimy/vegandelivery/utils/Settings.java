package com.nozimy.vegandelivery.utils;

import android.content.SharedPreferences;
import android.os.Bundle;

public class Settings {
    public static final String PLACES_CACHED = "com.nozimy.vegandelivery.PLACES_CACHED";
    private SharedPreferences sharedPreferences;

    public Settings(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    private void set(Bundle bundle){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(PLACES_CACHED, bundle.getBoolean(PLACES_CACHED));
        editor.apply();
    }

    private Bundle get(){
        Bundle bundle = new Bundle();
        bundle.putBoolean(PLACES_CACHED, sharedPreferences.getBoolean(PLACES_CACHED, false));
        return bundle;
    }

    public boolean getPlacesCached(){
        return get().getBoolean(PLACES_CACHED);
    }

    public void setPlacesCached(boolean isCached){
        Bundle settings = get();
        settings.putBoolean(PLACES_CACHED, isCached);
        set(settings);
    }
}
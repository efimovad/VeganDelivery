package com.nozimy.vegandelivery.di.app;

import com.nozimy.vegandelivery.app.App;
import com.nozimy.vegandelivery.di.places.PlacesComponent;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;

@Singleton
@Component(modules = {AppModule.class, DatabaseModule.class, RepositoryModule.class})
public interface AppComponent extends AndroidInjector<App> {
    PlacesComponent plusPlacesComponent();

    void inject(App app);
}

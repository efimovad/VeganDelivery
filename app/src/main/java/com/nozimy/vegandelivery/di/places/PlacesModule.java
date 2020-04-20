package com.nozimy.vegandelivery.di.places;

import com.nozimy.vegandelivery.db.DataRepository;
import com.nozimy.vegandelivery.di.scope.FragmentScope;
import com.nozimy.vegandelivery.interactors.places.PlacesListInteractor;
import com.nozimy.vegandelivery.interactors.places.PlacesListInteractorDefault;

import dagger.Module;
import dagger.Provides;

@Module
public class PlacesModule {
    @Provides
    @FragmentScope
    PlacesListInteractor provideContactListInteractor(DataRepository dataRepository){
        return new PlacesListInteractorDefault(dataRepository);
    }
}

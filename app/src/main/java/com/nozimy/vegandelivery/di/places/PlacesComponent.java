package com.nozimy.vegandelivery.di.places;

import com.nozimy.vegandelivery.di.scope.FragmentScope;
import com.nozimy.vegandelivery.ui.home.HomeFragment;

import dagger.Subcomponent;

@FragmentScope
@Subcomponent(modules = {PlacesModule.class})
public interface PlacesComponent {
    void inject(HomeFragment homeFragment);
}

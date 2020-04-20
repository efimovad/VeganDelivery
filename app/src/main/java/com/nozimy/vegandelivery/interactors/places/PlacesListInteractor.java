package com.nozimy.vegandelivery.interactors.places;

import androidx.lifecycle.LiveData;

import com.nozimy.vegandelivery.db.entity.PlaceEntity;
import com.nozimy.vegandelivery.interactors.BaseInteractor;

import java.util.List;

public interface PlacesListInteractor extends BaseInteractor {
    LiveData<List<PlaceEntity>> getPlaces();
}

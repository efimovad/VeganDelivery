package com.nozimy.vegandelivery.interactors.places;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.nozimy.vegandelivery.db.entity.DishEntity;
import com.nozimy.vegandelivery.db.entity.PlaceEntity;
import com.nozimy.vegandelivery.db.model.Dish;
import com.nozimy.vegandelivery.db.model.Place;
import com.nozimy.vegandelivery.interactors.BaseInteractor;
import com.nozimy.vegandelivery.network.ApiRepo;
import com.nozimy.vegandelivery.network.DishesApi;
import com.nozimy.vegandelivery.network.PlacesApi;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlacesListInteractor {
    private final Context mContext;
    private PlacesApi mPlacesApi;

    private final static MutableLiveData<List<Place>> mPlaces = new MutableLiveData<>();

    static {
        mPlaces.setValue(Collections.<Place>emptyList());
    }


    public PlacesListInteractor(Context context) {
        mContext = context;
        mPlacesApi = ApiRepo.from(mContext).getPlacesApi();
        refresh();
    }

    public LiveData<List<Place>> getPlaces() {
        return mPlaces;
    }

    public void refresh() {
        mPlacesApi.getAll().enqueue(new Callback<PlacesApi.PlacesResponse>() {
            @Override
            public void onResponse(Call<PlacesApi.PlacesResponse> call,
                                   Response<PlacesApi.PlacesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mPlaces.postValue(transform(response.body()));
                }
            }

            @Override
            public void onFailure(Call<PlacesApi.PlacesResponse> call, Throwable t) {
                Log.e("Places Interactor", "Failed to load", t);
            }
        });
    }

    private static List<Place> transform(PlacesApi.PlacesResponse placesResponse) {
        List<PlacesApi.PlacePlain> plains = placesResponse.places;
        List<Place> result = new ArrayList<>();
        for (PlacesApi.PlacePlain placePlain : plains) {
            try {
                PlaceEntity place = map(placePlain);
                result.add(place);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    private static PlaceEntity map(PlacesApi.PlacePlain placePlain) throws ParseException {
        return new PlaceEntity(
                placePlain.id,
                placePlain.name,
                placePlain.deliveryTime,
                placePlain.minCost,
                placePlain.grade
        );
    }
}

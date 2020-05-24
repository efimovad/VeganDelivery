package com.nozimy.vegandelivery.interactors.places;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.nozimy.vegandelivery.db.entity.PlaceEntity;
import com.nozimy.vegandelivery.db.model.MyPlace;
import com.nozimy.vegandelivery.network.ApiRepo;
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

    private final static MutableLiveData<List<MyPlace>> mPlaces = new MutableLiveData<>();

    static {
        mPlaces.setValue(Collections.<MyPlace>emptyList());
    }


    public PlacesListInteractor(Context context) {
        mContext = context;
        mPlacesApi = ApiRepo.from(mContext).getPlacesApi();
        refresh();
    }

    public LiveData<List<MyPlace>> getPlaces() {
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

    private static List<MyPlace> transform(PlacesApi.PlacesResponse placesResponse) {
        List<PlacesApi.PlacePlain> plains = placesResponse.places;
        List<MyPlace> result = new ArrayList<>();
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

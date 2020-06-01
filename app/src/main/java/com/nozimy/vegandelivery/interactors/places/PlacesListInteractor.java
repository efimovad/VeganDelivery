package com.nozimy.vegandelivery.interactors.places;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.nozimy.vegandelivery.db.DataRepository;
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
    private DataRepository mDataRepository;
    private SharedPreferences mSharedPreferences;
    private LiveData<List<PlaceEntity>> mPlaces;
    private LiveData<List<PlaceEntity>> favPlaces;

    private MediatorLiveData<List<PlaceEntity>> mPlaceLive = new MediatorLiveData<>();
    private MediatorLiveData<List<PlaceEntity>> mFavPlaceLive = new MediatorLiveData<>();

    static {
//        mPlaces.setValue(Collections.<MyPlace>emptyList());
    }


    public PlacesListInteractor(Context context) {
        mContext = context;
        mPlacesApi = ApiRepo.from(mContext).getPlacesApi();
        mDataRepository = new DataRepository(context);
        mPlaces = mDataRepository.getPlaces();

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        refresh();
    }

    public LiveData<List<PlaceEntity>> getPlaces() {

        final LiveData<List<PlaceEntity>> entities = mDataRepository.getPlaces();

        mPlaceLive.addSource(entities, placeList -> {
            if (placeList == null || placeList.isEmpty()) {
                refresh();
            } else {
                mPlaceLive.removeSource(entities);
                mPlaceLive.setValue(placeList);
            }
        });

        return mPlaceLive;
    }

    public LiveData<List<PlaceEntity>> getFavourite() {

        final LiveData<List<PlaceEntity>> entities = mDataRepository.getFavourite();

        mFavPlaceLive.addSource(entities, placeList -> {
            if (placeList != null) {
                mFavPlaceLive.removeSource(entities);
                mFavPlaceLive.setValue(placeList);
            }
        });

        return mFavPlaceLive;
    }


    public void refresh() {
        mPlacesApi.getAll().enqueue(new Callback<PlacesApi.PlacesResponse>() {
            @Override
            public void onResponse(Call<PlacesApi.PlacesResponse> call,
                                   Response<PlacesApi.PlacesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    importPlacesFromApiToDB(transform(response.body()));
//                    mPlaces.postValue(transform(response.body()));
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

    public static List<MyPlace> transformPlaceEntityToMyPlace(List<PlaceEntity> list) {
        List<MyPlace> result = new ArrayList<>();

        for (PlaceEntity entity : list) {
            result.add(entity);
        }

        return result;
    }


    private static PlaceEntity map(PlacesApi.PlacePlain placePlain) throws ParseException {
        return new PlaceEntity(
                placePlain.id,
                placePlain.name,
                placePlain.deliveryTime,
                placePlain.minCost,
                placePlain.grade,
                placePlain.image,
                placePlain.latitude,
                placePlain.longitude,
                placePlain.logo
        );
    }

    private void importPlacesFromApiToDB(List<MyPlace> places){
        for (MyPlace place: places) {
            mDataRepository.insertPlace((PlaceEntity) place);
        }
    }

    public void changeFavStatus(long id, boolean value) {
        mDataRepository.changeFavStatus(id, value);
    }
}

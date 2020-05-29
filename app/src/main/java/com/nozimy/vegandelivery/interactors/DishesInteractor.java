package com.nozimy.vegandelivery.interactors;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.nozimy.vegandelivery.db.entity.DishEntity;
import com.nozimy.vegandelivery.db.model.Dish;
import com.nozimy.vegandelivery.network.ApiRepo;
import com.nozimy.vegandelivery.network.DishesApi;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DishesInteractor {
    private final Context mContext;
    private DishesApi mDishesApi;

    private final static MutableLiveData<List<Dish>> mDishes = new MutableLiveData<>();

    static {
        mDishes.setValue(Collections.<Dish>emptyList());
    }

    public DishesInteractor(Context context) {
        mContext = context;
        mDishesApi = ApiRepo.from(mContext).getDishesApi();
    }

    public LiveData<List<Dish>> getDishes() {
        return mDishes;
    }

    public void refresh(int placeId) {
        mDishesApi.getAll(placeId).enqueue(new Callback<DishesApi.DishesResponse>() {
            @Override
            public void onResponse(Call<DishesApi.DishesResponse> call,
                                   Response<DishesApi.DishesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mDishes.postValue(transform(response.body()));
                }
            }

            @Override
            public void onFailure(Call<DishesApi.DishesResponse> call, Throwable t) {
                Log.e("DishesInteractor", "Failed to load", t);
            }
        });
    }

    private static List<Dish> transform(DishesApi.DishesResponse dishesResponse) {
        List<DishesApi.DishPlain> plains = dishesResponse.dishes;
        List<Dish> result = new ArrayList<>();
        if (plains != null) {
            for (DishesApi.DishPlain dishPlain : plains) {
                try {
                    DishEntity dish = map(dishPlain);
                    if (dish != null) {
                        result.add(dish);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }


    private static DishEntity map(DishesApi.DishPlain dishPlain) throws ParseException {
        return new DishEntity(
                dishPlain.id,
                dishPlain.name,
                dishPlain.cost,
                dishPlain.calories,
                dishPlain.weight,
                dishPlain.ingredients,
                dishPlain.image
        );
    }
}

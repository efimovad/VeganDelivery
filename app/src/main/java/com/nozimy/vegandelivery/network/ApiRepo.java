package com.nozimy.vegandelivery.network;

import android.content.Context;

import com.nozimy.vegandelivery.app.App;

import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class ApiRepo {
    static final String BACKEND_BASE_URL = "https://vegan-delivery-api.herokuapp.com/api/v1/";

    private final OkHttpClient mOkHttpClient;
    private final PlacesApi mPlacesApi;
    private final DishesApi mDishesApi;

    public ApiRepo(){

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BACKEND_BASE_URL)
//                .addConverterFactory(MoshiConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
//                .build();

        mOkHttpClient = new OkHttpClient()
                .newBuilder()
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(BACKEND_BASE_URL)
                .client(mOkHttpClient)
                .build();

        mPlacesApi = retrofit.create(PlacesApi.class);
        mDishesApi = retrofit.create(DishesApi.class);
    }

    public PlacesApi getPlacesApi(){
        return mPlacesApi;
    }

    public DishesApi getDishesApi(){
        return mDishesApi;
    }

    public static ApiRepo from(Context context) {
        return App.from(context).getApis();
    }
}

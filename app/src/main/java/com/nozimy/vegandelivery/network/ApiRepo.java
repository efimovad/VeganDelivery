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
    static final String BACKEND_HOST = "vegan-delivery-api.herokuapp.com";
    static final String PROTOCOL = "https";

    private final OkHttpClient mOkHttpClient;
    private final PlacesApi mPlacesApi;
    private final DishesApi mDishesApi;
    private final OrdersApi mOrdersApi;

    public ApiRepo(){
        mOkHttpClient = new OkHttpClient()
                .newBuilder()
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(new HttpUrl.Builder().scheme(PROTOCOL)
                        .host(BACKEND_HOST)
                        .build())
                .client(mOkHttpClient)
                .build();

        mPlacesApi = retrofit.create(PlacesApi.class);
        mDishesApi = retrofit.create(DishesApi.class);
        mOrdersApi = retrofit.create(OrdersApi.class);
    }

    public PlacesApi getPlacesApi(){ return mPlacesApi; }

    public OrdersApi getOrdersApi() { return mOrdersApi; }

    public DishesApi getDishesApi(){
        return mDishesApi;
    }

    public static ApiRepo from(Context context) {
        return App.from(context).getApis();
    }
}

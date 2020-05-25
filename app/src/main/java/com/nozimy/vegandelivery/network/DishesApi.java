package com.nozimy.vegandelivery.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DishesApi {
    class DishPlain {
        public int id;
        public String  name;
        public String  ingredients;
        public int  calories;
        public int  weight;
        public int  cost;
        public String  image;
    }

    class DishesResponse {
        public List<DishPlain> dishes;
    }

    @GET("/api/v1/dishes/{id}")
    Call<DishesResponse> getAll(@Path("id") int placeId);
}

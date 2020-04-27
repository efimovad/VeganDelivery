package com.nozimy.vegandelivery.network;

import com.nozimy.vegandelivery.db.entity.PlaceEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlacesApi {

    class PlacePlain {
        public int id;
        public String name;
        public int deliveryTime;
        public int minCost;
        public float grade;
    }

    class PlacesResponse {
        public List<PlacePlain> places;
    }

    @GET("/api/v1/places")
    Call<PlacesResponse> getAll();
}

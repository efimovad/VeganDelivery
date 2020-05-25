package com.nozimy.vegandelivery.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PlacesApi {

    class PlacePlain {
        public int id;
        public String name;
        public int deliveryTime;
        public int minCost;
        public float grade;
        public float longitude;
        public float latitude;
        public String image;
    }

    class PlacesResponse {
        public List<PlacePlain> places;
    }

    @GET("/api/v1/places")
    Call<PlacesResponse> getAll();
}

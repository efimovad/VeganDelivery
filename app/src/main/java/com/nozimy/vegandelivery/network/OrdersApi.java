package com.nozimy.vegandelivery.network;

import androidx.cardview.widget.CardView;

import com.nozimy.vegandelivery.db.entity.ItemEntity;
import com.nozimy.vegandelivery.db.model.Item;
import com.nozimy.vegandelivery.db.model.Order;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OrdersApi {
    class OrderPlain {
        public int id;
        public String cafeName;
        public String cafeLogo;
        public String address;
        public int cost;
        public List<ItemEntity> itemsFull = null;;
        public int status;
        //public int user;
    }

    class OrderResponse {
        public List<OrdersApi.OrderPlain> orders;
    }

    @GET("/api/v1/order/{id}")
    Call<OrdersApi.OrderResponse> getAll(@Path("id") String user);

    @POST("/api/v1/order")
    Call<OrderRequest> createOrder(@Body OrderRequest request);
}

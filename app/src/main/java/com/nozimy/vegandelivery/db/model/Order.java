package com.nozimy.vegandelivery.db.model;

import com.google.android.gms.maps.model.LatLng;
import com.nozimy.vegandelivery.db.entity.ItemEntity;

import java.util.List;


public interface Order {

    int Created = 0;
    int Preparing = 1;
    int Delivering = 2;
    int Delivered = 3;
    int Canceled = 4;

    int increment(Dish dish, MyPlace place);
    int decrement(Dish dish);

    int size();
    void clear();
    boolean isEmpty();

    String getCafeName();
    String getLogo();
    String getName(int position);
    String getPrice(int position);
    String getTotalPrice();
    int getCost();

    int getCount(Dish dish);
    int getCount(int position);
    int getStatus();
    int getCafeId();

    String getUser();
    void setUser(String user);

    void setUserAddress(String address, LatLng latLng);
    LatLng getShopLatLng();
    LatLng getUserLatLng();
    String getUserAddress();

    String toString();
    List<ItemEntity> getItems();

    boolean isTotalPriceEnough();
    int getId();
}

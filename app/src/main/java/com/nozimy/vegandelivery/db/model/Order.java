package com.nozimy.vegandelivery.db.model;

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
    String getAddress();

    int getCost();
    int getCount(Dish dish);
    int getCount(int position);
    int getStatus();
    int getCafeId();

    String toString();
    List<ItemEntity> getItems();
}

package com.nozimy.vegandelivery.db.model;

import android.content.ClipData;

public interface Order {
    int increment(Dish dish);
    int decrement(Dish dish);
    int getCount(Dish dish);

    void clear();
    int size();
    boolean isEmpty();

    String getName(int position);
    String getPrice(int position);
    int getCount(int position);

    String toString();
}

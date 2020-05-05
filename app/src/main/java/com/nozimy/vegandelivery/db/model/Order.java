package com.nozimy.vegandelivery.db.model;

import android.content.ClipData;

public interface Order {
    void increment(Dish dish);
    void decrement(Dish dish);
    void clear();
    int size();
    boolean isEmpty();

    String getName(int position);
    String getPrice(int position);
    int getCount(int position);

    String toString();
}

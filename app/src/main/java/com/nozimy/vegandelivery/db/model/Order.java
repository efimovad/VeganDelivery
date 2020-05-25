package com.nozimy.vegandelivery.db.model;

import com.nozimy.vegandelivery.db.entity.Item;

import java.util.ArrayList;

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

    ArrayList<Item> getItems();
}

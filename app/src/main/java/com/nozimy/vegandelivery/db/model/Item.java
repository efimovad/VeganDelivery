package com.nozimy.vegandelivery.db.model;

public interface Item {
    String getName();
    int getId();
    int getCount();
    int getPrice();

    void subCount(int num);
    void addCount(int num);
}

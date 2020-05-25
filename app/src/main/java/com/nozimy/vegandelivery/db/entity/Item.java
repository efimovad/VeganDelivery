package com.nozimy.vegandelivery.db.entity;

import com.nozimy.vegandelivery.db.model.Dish;

public class Item {
    Dish dish;
    int count;

    public Item(Dish dish) {
        this.dish = dish;
        this.count = 1;
    }
}

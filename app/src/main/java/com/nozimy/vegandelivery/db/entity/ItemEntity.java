package com.nozimy.vegandelivery.db.entity;

import com.google.gson.annotations.SerializedName;
import com.nozimy.vegandelivery.db.model.Dish;
import com.nozimy.vegandelivery.db.model.Item;

public class ItemEntity implements Item {
    @SerializedName("dish")
    private int id;
    String name;
    int count;
    int price;

    public ItemEntity(String name, int count, int price) {
        this.name = name;
        this.count = count;
        this.price = price;
    }

    public ItemEntity(Dish dish) {
        this.id = dish.getId();
        this.name = dish.getName();
        this.price = dish.getCost();
        this.count = 1;
    }

    public int getId() { return id; }

    public String getName() { return name; }

    public int getCount() { return count; }

    public int getPrice() { return price; }

    @Override
    public void subCount(int num) {
        this.count -= num;
    }

    @Override
    public void addCount(int num) { this.count += num; }
}

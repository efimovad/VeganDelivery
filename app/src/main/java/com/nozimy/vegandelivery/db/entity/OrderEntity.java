package com.nozimy.vegandelivery.db.entity;

import android.annotation.SuppressLint;

import com.nozimy.vegandelivery.db.model.Dish;
import com.nozimy.vegandelivery.db.model.MyPlace;
import com.nozimy.vegandelivery.db.model.Order;

import java.util.ArrayList;

public class OrderEntity implements Order {
    private String address;
    private ArrayList<Item> mItems = new ArrayList<>();
    private int mTotalPrice = 0;
    private MyPlace mMyPlace;

    private int find(Dish dish) {
        int id = dish.getId();
        for (int i = 0; i < mItems.size(); i++) {
            if (mItems.get(i).dish.getId() == id) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int increment(Dish dish) {
        int position = find(dish);

        int res = 1;
        if (position != -1) {
            res = ++mItems.get(position).count;
        } else {
            Item item = new Item(dish);
            mItems.add(item);
        }
        mTotalPrice += dish.getCost();
        return res;
    }

    @Override
    public int decrement(Dish dish) {
        int position = find(dish);
        if (position == -1) {
            return 0;
        }

        if (mItems.get(position).count == 1) {
            mItems.remove(position);
            return 0;
        }
        mItems.get(position).count--;
        mTotalPrice -= dish.getCost();
        return mItems.get(position).count;
    }

    @Override
    public int getCount(Dish dish) {
        int position = find(dish);
        if (position == -1) {
            return 0;
        }

        return mItems.get(position).count;
    }

    @Override
    public void clear() {
        if (!mItems.isEmpty()) {
            mItems.clear();
        }
        mTotalPrice = 0;
        mMyPlace = null;
    }

    @Override
    public boolean isEmpty() {
        return mItems.isEmpty();
    }

    @Override
    public String getName(int position) {
        return mItems.get(position).dish.getName();
    }

    @Override
    public int getCount(int position) {
        return mItems.get(position).count;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public String getPrice(int position) {
        return String.format("%d \u20BD",
                mItems.get(position).dish.getCost() * mItems.get(position).count);
    }

    @Override
    public String toString() {
        String res = "";

        for (int i = 0; i < mItems.size(); i++) {
            Item value = mItems.get(i);
            res = res + value.dish.getName() + " " + value.count + "\n";
        }

        return res;
    }

    @Override
    public ArrayList<Item> getItems() {
        return mItems;
    }

    @Override
    public int size() {
        return mItems.size();
    }
}

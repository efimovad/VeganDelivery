package com.nozimy.vegandelivery.db.entity;

import com.nozimy.vegandelivery.db.model.Dish;
import com.nozimy.vegandelivery.db.model.Order;

import java.util.ArrayList;

public class OrderEntity implements Order {
    private ArrayList<Item> mItems = new ArrayList<>();

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
    public void increment(Dish dish) {
        int id = find(dish);
        if (id != -1) {
            mItems.get(id).count++;
        } else {
            Item item = new Item(dish);
            mItems.add(item);
        }
    }

    @Override
    public void decrement(Dish dish) {
        int id = find(dish);
        if (id != -1) {
            return;
        }

        if (mItems.get(id).count == 1) {
            mItems.remove(id);
            return;
        }
        mItems.get(id).count--;
    }

    @Override
    public void clear() {
        mItems.clear();
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

    @Override
    public String getPrice(int position) {
        return mItems.get(position).dish.getCostString();
    }

    private class Item {
        Dish dish;
        int count;

        public Item(Dish dish) {
            this.dish = dish;
            this.count = 1;
        }
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
    public int size() {
        return mItems.size();
    }




}

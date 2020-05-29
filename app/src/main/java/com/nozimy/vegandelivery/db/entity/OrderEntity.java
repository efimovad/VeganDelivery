package com.nozimy.vegandelivery.db.entity;

import android.annotation.SuppressLint;

import com.google.gson.annotations.SerializedName;
import com.nozimy.vegandelivery.db.model.Dish;
import com.nozimy.vegandelivery.db.model.Item;
import com.nozimy.vegandelivery.db.model.MyPlace;
import com.nozimy.vegandelivery.db.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderEntity implements Order {
    private List<ItemEntity> mItems = new ArrayList<>();
    private int mTotalPrice = 0;
    private String mAddress;
    private String mLogo;
    private int mCafeId;
    private String mCafeName;
    private int mStatus;

    public OrderEntity() {}

    public int getStatus() {
        return mStatus;
    }

    @Override
    public int getCafeId() { return mCafeId; }

    public OrderEntity(List<ItemEntity> mItems, int mTotalPrice, String mAddress, String mLogo, String mCafeName, int mStatus) {
        this.mItems = mItems;
        this.mTotalPrice = mTotalPrice;
        this.mAddress = mAddress;
        this.mLogo = mLogo;
        this.mCafeName = mCafeName;
        this.mStatus = mStatus;
    }

    private int find(Dish dish) {
        int id = dish.getId();
        for (int i = 0; i < mItems.size(); i++) {
            if (mItems.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int increment(Dish dish, MyPlace place) {
        int position = find(dish);

        if (mCafeId == 0) {
            mCafeId = place.getId();
            mCafeName = place.getName();
            mLogo = place.getLogo();
        } else if (mCafeId != place.getId()) {
            return -1;
        }

        int res = 1;
        if (position != -1) {
            mItems.get(position).addCount(1);
            res = mItems.get(position).getCount();
        } else {
            ItemEntity item = new ItemEntity(dish);
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

        if (mItems.get(position).getCount() == 1) {
            mItems.remove(position);
            return 0;
        }

        mItems.get(position).subCount(1);
        mTotalPrice -= dish.getCost();
        return mItems.get(position).getCount();
    }

    @Override
    public int getCount(Dish dish) {
        int position = find(dish);
        if (position == -1) {
            return 0;
        }

        return mItems.get(position).getCount();
    }

    @Override
    public void clear() {
        if (!mItems.isEmpty()) {
            mItems.clear();
        }
        mTotalPrice = 0;
        mCafeId = 0;
    }

    @Override
    public boolean isEmpty() {
        return mItems.isEmpty();
    }

    @Override
    public String getCafeName() {
        return mCafeName;
    }

    @Override
    public String getLogo() {
        return mLogo;
    }

    @Override
    public String getName(int position) {
        return mItems.get(position).getName();
    }

    @Override
    public int getCount(int position) {
        return mItems.get(position).getCount();
    }

    @SuppressLint("DefaultLocale")
    @Override
    public String getPrice(int position) {
        return String.format("%d \u20BD",
                mItems.get(position).getCount() * mItems.get(position).getPrice());
    }

    @Override
    public String getTotalPrice() {
        return String.format("%d \u20BD", mTotalPrice);
    }

    @Override
    public String getAddress() { return mAddress; }

    @Override
    public int getCost() {
        return mTotalPrice;
    }

    @Override
    public String toString() {
        String res = "";

        for (int i = 0; i < mItems.size(); i++) {
            Item value = mItems.get(i);
            res = res + value.getName() + " " + value.getCount() + "\n";
        }

        return res;
    }

    @Override
    public List<ItemEntity> getItems() {
        return mItems;
    }

    @Override
    public int size() {
        return mItems.size();
    }
}

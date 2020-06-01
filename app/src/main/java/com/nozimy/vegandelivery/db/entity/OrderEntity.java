package com.nozimy.vegandelivery.db.entity;

import android.annotation.SuppressLint;

import com.google.android.gms.maps.model.LatLng;
import com.nozimy.vegandelivery.db.model.Dish;
import com.nozimy.vegandelivery.db.model.Item;
import com.nozimy.vegandelivery.db.model.MyPlace;
import com.nozimy.vegandelivery.db.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderEntity implements Order {
    private int id;
    private List<ItemEntity> mItems = new ArrayList<>();

    private String user;

    private int mCafeId;
    private String mLogo;
    private String mCafeName;
    private int minPrice;

    private int mStatus;
    private int mTotalPrice = 0;

    private Address shopAddress;
    private Address userAddress;

    class Address {
        String address;
        LatLng latLng;
        String flat;
        String porch;
        String floor;

        Address(String address, LatLng latLng) {
            this.address = address;
            this.latLng = latLng;
        }

        Address(String address, float latitude, float longitude) {
            this.address = address;
            this.latLng = new LatLng(latitude, longitude);
        }

        void setAddressParams(String flat, String porch, String floor) {
            this.flat = flat;
            this.porch = porch;
            this.floor = floor;
        }
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public LatLng getShopLatLng() {
        return shopAddress.latLng;
    }

    public LatLng getUserLatLng() {
        if (userAddress == null) {
            return null;
        }
        return userAddress.latLng;
    }

    @Override
    public void setUserAddress(String address, LatLng latLng) {
        userAddress = new Address(address, latLng);
    }

    public String getUserAddress() {
        if (userAddress == null) {
            return "";
        }
        return userAddress.address;
    }


    public OrderEntity() {}

    public int getStatus() {
        return mStatus;
    }

    @Override
    public int getCafeId() { return mCafeId; }

    public boolean isTotalPriceEnough() {
        return this.mTotalPrice >= minPrice;
    }

    public OrderEntity(int id, List<ItemEntity> mItems, int mTotalPrice, String mAddress, String mLogo, String mCafeName, int mStatus) {
        this.id = id;
        this.mItems = mItems;
        this.mTotalPrice = mTotalPrice;
        this.mLogo = mLogo;
        this.mCafeName = mCafeName;
        this.mStatus = mStatus;
    }

    public int find(Dish dish) {
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
            minPrice = place.getMinOrderCost();
            LatLng latLng = new LatLng(place.getLatitude(), place.getLongitude());
            shopAddress = new Address("", latLng);
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

    public int getId() { return id; }
}

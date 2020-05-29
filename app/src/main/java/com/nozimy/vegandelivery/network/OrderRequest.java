package com.nozimy.vegandelivery.network;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nozimy.vegandelivery.db.entity.ItemEntity;
import com.nozimy.vegandelivery.db.model.Order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderRequest implements Serializable {
    @SerializedName("user")
    @Expose
    int user;

    @SerializedName("cafe")
    @Expose
    int cafe;

    @SerializedName("cost")
    @Expose
    int cost;

    @SerializedName("address")
    @Expose
    String address;

    @SerializedName("items")
    @Expose
    List<Item> items;

    public int getUser() {
        return user;
    }

    public int getCafe() {
        return cafe;
    }

    public int getCost() {
        return cost;
    }

    public String getAddress() {
        return address;
    }

    public List<Item> getItems() {
        return items;
    }

    static class Item implements Serializable {
        @SerializedName("dish")
        @Expose
        int dish;

        @SerializedName("count")
        @Expose
        int count;

        Item(ItemEntity itemFull) {
           this.dish = itemFull.getId();
           this.count = itemFull.getCount();
        }

        public int getDish() {
            return dish;
        }

        public int getCount() {
            return count;
        }
    }

    public OrderRequest(int user, Order order) {
        this.user = user;
        this.cafe = order.getCafeId();
        this.cost = order.getCost();
        this.address = order.getAddress();
        this.items = new ArrayList<>();

        List<ItemEntity> itemsFull = order.getItems();
        for (int i = 0; i < itemsFull.size(); i++) {
            Item item = new Item(itemsFull.get(i));
            this.items.add(item);
        }
    }
}

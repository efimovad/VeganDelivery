package com.nozimy.vegandelivery.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.nozimy.vegandelivery.db.model.Place;

@Entity(tableName = "places", indices = {@Index("name")})
public class PlaceEntity implements Place {
    @PrimaryKey
    @NonNull
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "deliveryTime")
    private int deliveryTime;

    @ColumnInfo(name = "minOrderCost")
    private int minOrderCost;

    @ColumnInfo(name = "grade")
    private float grade;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDeliveryTime() {
        return String.format("%d мин", this.deliveryTime);
    }

    @Override
    public String getMinOrderCost() {
        return String.format("Заказ от %d \u20BD", this.minOrderCost);
    }

    @Override
    public String getGrade() {
        return String.format("%.2f", this.grade);
    }

    public PlaceEntity(int id, String name, int deliveryTime, int minOrderCost, float grade){
        this.id = id;
        this.name = name;
        this.deliveryTime = deliveryTime;
        this.minOrderCost = minOrderCost;
        this.grade = grade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDeliveryTime(int deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public void setMinOrderCost(int minOrderCost) {
        this.minOrderCost = minOrderCost;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }
}

package com.nozimy.vegandelivery.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.nozimy.vegandelivery.db.model.MyPlace;

@Entity(tableName = "places", indices = {@Index("name")})
public class PlaceEntity implements MyPlace {
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

    @ColumnInfo(name = "longitude")
    private float longitude;

    @ColumnInfo(name = "latitude")
    private float latitude;

    @ColumnInfo(name = "image")
    private String image;

    public String getLogo() {
        return logo;
    }

    @ColumnInfo(name = "logo")
    private String logo;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDeliveryTimeString() {
        return String.format("%d мин", this.deliveryTime);
    }

    @Override
    public String getMinOrderCostString() {
        return String.format("Заказ от %d \u20BD", this.minOrderCost);
    }

    @Override
    public String getGradeString() {
        return String.format("%.2f", this.grade);
    }

    @Override
    public String getImage() {
        return image;
    }

    @Override
    public float getLatitude() {
        return latitude;
    }

    @Override
    public float getLongitude() {
        return longitude;
    }


    public PlaceEntity(int id, String name, int deliveryTime, int minOrderCost, float grade,
                       String image, float latitude, float longitude, String logo){
        this.id = id;
        this.name = name;
        this.deliveryTime = deliveryTime;
        this.minOrderCost = minOrderCost;
        this.grade = grade;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
        this.logo = logo;
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

    public void setImage(String image) { this.image = image; }

    public void setDeliveryTime(int deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public void setMinOrderCost(int minOrderCost) {
        this.minOrderCost = minOrderCost;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public void setLongitude(float longitude) { this.longitude = longitude; }

    public void setLatitude(float latitude) { this.latitude = latitude; }

    public int getDeliveryTime() {
        return deliveryTime;
    }

    public int getMinOrderCost() {
        return minOrderCost;
    }

    public float getGrade() {
        return grade;
    }
}

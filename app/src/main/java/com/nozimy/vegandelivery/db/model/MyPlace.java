package com.nozimy.vegandelivery.db.model;

public interface MyPlace {
    String getName();
    String getDeliveryTimeString();
    String getMinOrderCostString();
    String getGradeString();
    String getImage();

    float getLatitude();
    float getLongitude();
    int getId();
}

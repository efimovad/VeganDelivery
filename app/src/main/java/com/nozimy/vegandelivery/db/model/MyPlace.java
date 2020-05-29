package com.nozimy.vegandelivery.db.model;

public interface MyPlace {
    String getName();
    String getDeliveryTimeString();
    String getMinOrderCostString();
    String getGradeString();
    String getImage();
    String getLogo();

    float getLatitude();
    float getLongitude();
    int getId();
}

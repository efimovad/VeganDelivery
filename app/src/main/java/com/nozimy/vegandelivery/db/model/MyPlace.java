package com.nozimy.vegandelivery.db.model;

public interface MyPlace {
    String getName();
    String getDeliveryTimeString();
    String getMinOrderCostString();
    String getGradeString();
    String getImage();
    String getLogo();

    boolean getFavourite();

    float getLatitude();
    float getLongitude();
    int getId();
    int getMinOrderCost();

    void setFavourite(boolean favourite);
}

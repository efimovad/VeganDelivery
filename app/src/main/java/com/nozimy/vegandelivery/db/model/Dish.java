package com.nozimy.vegandelivery.db.model;

public interface Dish {
    int getId();
    String getName();
    String getCostString();
    int getCost();
    String getWeightString();
    String getСalories();
    String getIngredients();
    String getImage();
}

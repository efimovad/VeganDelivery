package com.nozimy.vegandelivery.db.entity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.nozimy.vegandelivery.db.model.Dish;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Entity(tableName = "dishes", indices = {@Index("name")})
public class DishEntity implements Dish {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "cost")
    private int cost;

    @ColumnInfo(name = "weight")
    private int weight;

    @ColumnInfo(name = "calories")
    private int calories;

    @ColumnInfo(name = "ingredients")
    private String ingredients;

    @ColumnInfo(name = "image")
    private String image;

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getCostString() {
        return String.format("%d \u20BD", this.cost);
    }

    @Override
    public String getWeightString() {
        return String.format("%d г", this.weight);
    }

    @Override
    public String getСalories() {
        return String.format("%d ккал", this.calories);
    }

    @Override
    public String getIngredients() {return this.ingredients;}

    @Override
    public String getImage() {return this.image;}

    public DishEntity(String name, int cost, int calories,
                      int weight, String ingredients, String imageURL){
        this.name = name;
        this.cost = cost;
        this.calories = calories;
        this.weight = weight;
        this.ingredients = ingredients;
        this.image = imageURL;
    }

    public DishEntity(int id, String name, int cost, int weight, int calories, String ingredients, String image) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.weight = weight;
        this.calories = calories;
        this.ingredients = ingredients;
        this.image = image;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCost() {
        return cost;
    }

    public int getWeight() {
        return weight;
    }
}

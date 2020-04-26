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

@Entity(tableName = "places", indices = {@Index("name")})
public class DishEntity implements Dish {
    @PrimaryKey
    @NonNull
    private String id;

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

    @ColumnInfo(name = "")

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getCost() {
        return String.format("%d \u20BD", this.cost);
    }

    @Override
    public String getWeight() {
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
}

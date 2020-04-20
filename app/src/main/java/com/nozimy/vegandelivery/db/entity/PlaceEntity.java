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
    private String id;

    @ColumnInfo(name = "name")
    private String name;

    @Override
    public String getName() {
        return null;
    }

    public PlaceEntity(String id, String name){
        this.id = id;
        this.name = name;
    }



}

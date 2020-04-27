package com.nozimy.vegandelivery.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.nozimy.vegandelivery.db.model.Person;

@Entity(tableName = "accounts", indices = {@Index("name")})
public class PersonEntity implements Person {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ColumnInfo(name = "name")
    private String name;
}

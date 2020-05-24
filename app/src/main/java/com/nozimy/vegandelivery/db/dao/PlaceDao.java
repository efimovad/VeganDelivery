package com.nozimy.vegandelivery.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.nozimy.vegandelivery.db.entity.PlaceEntity;

import java.util.List;

@Dao
public interface PlaceDao {
    @Query("SELECT * FROM places")
    LiveData<List<PlaceEntity>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(PlaceEntity placeEntity);

}

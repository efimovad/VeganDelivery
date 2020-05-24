package com.nozimy.vegandelivery.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.nozimy.vegandelivery.db.entity.PersonEntity;

@Dao
public interface PersonDao {
    @Query("SELECT * FROM accounts WHERE id=:id")
    LiveData<PersonEntity> getById(String id);

    @Insert
    void insert(PersonEntity personEntity);

    @Query("UPDATE accounts SET name=:name WHERE id= :id")
    void updateName(String id, String name);

    @Query("UPDATE accounts SET phone=:phone WHERE id= :id")
    void updatePhone(String id, String phone);

    @Query("UPDATE accounts SET email=:email WHERE id= :id")
    void updateEmail(String id, String email);

    @Query("UPDATE accounts SET name=:name, phone=:phone, email=:email WHERE id= :id")
    void update(String id, String name, String phone, String email);
}

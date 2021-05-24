package com.hasib.petzone.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insertUser(User user);

    @Update
    void updateUser(User person);

    @Delete
    void deleteUser(User person);

    @Query("DELETE FROM user_table")
    void deleteAllUsers();

    @Query("SELECT * FROM user_table ORDER BY ID")
    LiveData<List<User>> loadAllUsers();

    @Query("SELECT * FROM user_table WHERE id = :id")
    User loadUserById(int id);
}

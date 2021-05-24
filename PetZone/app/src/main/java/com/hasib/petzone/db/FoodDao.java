package com.hasib.petzone.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FoodDao {

    @Insert
    void insertFood(Food user);

    @Update
    void updateFood(Food person);

    @Delete
    void deleteFood(Food person);

    @Query("DELETE FROM food_table")
    void deleteAllFoods();

    @Query("SELECT * FROM food_table ORDER BY ID")
    LiveData<List<Food>> loadAllFoods();

    @Query("SELECT * FROM food_table WHERE id = :id")
    Food loadFoodById(int id);
}

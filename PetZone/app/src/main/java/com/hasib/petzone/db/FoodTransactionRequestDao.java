package com.hasib.petzone.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FoodTransactionRequestDao {

    @Insert
    void insertFoodTransaction(FoodTransactionRequest user);

    @Update
    void updateFoodTransaction(FoodTransactionRequest person);

    @Delete
    void deleteFoodTransaction(FoodTransactionRequest person);

    @Query("DELETE FROM food_table")
    void deleteAllFoodTransactions();

    @Query("SELECT * FROM food_transaction_table ORDER BY ID")
    LiveData<List<FoodTransactionRequest>> loadAllFoodTransactions();

    @Query("SELECT * FROM food_transaction_table WHERE transactionId = :transactionId")
    FoodTransactionRequest loadFoodTransactionById(String transactionId);
}

package com.hasib.petzone.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FoodTransactionHistoryDao {

    @Insert
    void insertFoodTransaction(FoodTransactionHistory user);

    @Update
    void updateFoodTransaction(FoodTransactionHistory person);

    @Delete
    void deleteFoodTransaction(FoodTransactionHistory person);

    @Query("DELETE FROM food_table")
    void deleteAllFoodTransactions();

    @Query("SELECT * FROM food_transaction_history_table ORDER BY ID")
    LiveData<List<FoodTransactionHistory>> loadAllFoodTransactions();

    @Query("SELECT * FROM food_transaction_history_table WHERE transactionId = :transactionId")
    FoodTransactionHistory loadFoodTransactionById(String transactionId);
}

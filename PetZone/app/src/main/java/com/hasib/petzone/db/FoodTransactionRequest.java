package com.hasib.petzone.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;
import java.util.UUID;

@Entity(tableName = "food_transaction_table")
public class FoodTransactionRequest {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String transactionId;
    private List<Food> foods;
    private User buyer;

    public FoodTransactionRequest(String transactionId, List<Food> foods, User buyer) {
        this.transactionId = transactionId;
        this.foods = foods;
        this.buyer = buyer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public User getBuyer() {
        return buyer;
    }

    @Override
    public String toString() {
        return "FoodTransaction{" +
                "id=" + id +
                ", transactionId='" + transactionId + '\'' +
                ", foods=" + foods +
                ", buyer=" + buyer +
                '}';
    }
}

package com.hasib.petzone;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.hasib.petzone.db.AppRepository;
import com.hasib.petzone.db.Food;
import com.hasib.petzone.db.FoodTransactionHistory;
import com.hasib.petzone.db.FoodTransactionRequest;
import com.hasib.petzone.db.User;

import java.util.List;

public class AppViewModel extends AndroidViewModel {
    private final AppRepository repository;
    private final LiveData<List<Food>> foods;
    private final LiveData<List<FoodTransactionRequest>> transactions;
    private final LiveData<List<FoodTransactionHistory>> transactionsHistory;
    private final LiveData<List<User>> users;

    public AppViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);

        foods = repository.getFoods();
        transactions = repository.getTransactions();
        transactionsHistory = repository.getTransactionsHistory();
        users = repository.getUsers();
    }

    public void insertUser(User user) {
        repository.insertUser(user);
    }

    public void updateUser(User user) {
        repository.updateUser(user);
    }

    public void deleteUser(User user) {
        repository.deleteUser(user);
    }

    public void deleteAllUsers() {
        repository.deleteAllUsers();
    }

    public LiveData<List<User>> getUsers() { return users; }

    public void insertFood(Food food) {
        repository.insertFood(food);
    }

    public void updateFood(Food food) {
        repository.updateFood(food);
    }

    public void deleteFood(Food food) {
        repository.deleteFood(food);
    }

    public void deleteAllFoods() {
        repository.deleteAllFoods();
    }

    public LiveData<List<Food>> getFoods() {
        return foods;
    }

    public void insertFoodTransaction(FoodTransactionRequest transaction) {
        repository.insertFoodTransaction(transaction);
    }

    public void updateFoodTransaction(FoodTransactionRequest transaction) {
        repository.updateFoodTransaction(transaction);
    }

    public void deleteFoodTransaction(FoodTransactionRequest transaction) {
        repository.deleteFoodTransaction(transaction);
    }

    public void deleteAllFoodTransactions() {
        repository.deleteAllFoodTransactions();
    }

    public LiveData<List<FoodTransactionRequest>> getTransactions() {
        return transactions;
    }

    public void insertFoodTransactionHistory(FoodTransactionHistory transaction) {
        repository.insertFoodTransactionHistory(transaction);
    }

    public void updateFoodTransactionHistory(FoodTransactionHistory transaction) {
        repository.updateFoodTransactionHistory(transaction);
    }

    public void deleteFoodTransactionHistory(FoodTransactionHistory transaction) {
        repository.deleteFoodTransactionHistory(transaction);
    }

    public void deleteAllFoodTransactionsHistory() {
        repository.deleteAllFoodTransactionsHistory();
    }

    public LiveData<List<FoodTransactionHistory>> getTransactionsHistory() {
        return transactionsHistory;
    }
}

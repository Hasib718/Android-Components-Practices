package com.hasib.petzone.db;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AppRepository {

    private static final String TAG = AppRepository.class.getSimpleName();

    private final UserDao userDao;
    private final FoodDao foodDao;
    private final FoodTransactionRequestDao foodTransactionRequestDao;
    private final FoodTransactionHistoryDao foodTransactionHistoryDao;
    private final LiveData<List<Food>> foods;
    private final LiveData<List<FoodTransactionRequest>> transactions;
    private final LiveData<List<FoodTransactionHistory>> transactionsHistory;
    private LiveData<List<User>> users;

    public AppRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);

        userDao = database.userDao();
        foodDao = database.foodDao();
        foodTransactionRequestDao = database.foodTransactionDao();
        foodTransactionHistoryDao = database.foodTransactionHistoryDao();

        foods = foodDao.loadAllFoods();
        transactions = foodTransactionRequestDao.loadAllFoodTransactions();
        users = userDao.loadAllUsers();
        transactionsHistory = foodTransactionHistoryDao.loadAllFoodTransactions();
    }

    public void insertUser(User user) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                userDao.insertUser(user);
            }
        });
    }

    public void updateUser(User user) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                userDao.updateUser(user);
            }
        });
    }

    public void deleteUser(User user) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                userDao.deleteUser(user);
            }
        });
    }

    public LiveData<List<User>> getUsers() {
        return users;
    }

    public void deleteAllUsers() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                userDao.deleteAllUsers();
            }
        });
    }

    public void insertFood(Food food) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                foodDao.insertFood(food);
            }
        });
    }

    public void updateFood(Food food) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                foodDao.updateFood(food);
            }
        });
    }

    public void deleteFood(Food food) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                foodDao.deleteFood(food);
            }
        });
    }

    public void deleteAllFoods() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                foodDao.deleteAllFoods();
            }
        });
    }

    public LiveData<List<Food>> getFoods() {
        return foods;
    }

    public void insertFoodTransaction(FoodTransactionRequest transaction) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                foodTransactionRequestDao.insertFoodTransaction(transaction);
            }
        });
    }

    public void updateFoodTransaction(FoodTransactionRequest transaction) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                foodTransactionRequestDao.updateFoodTransaction(transaction);
            }
        });
    }

    public void deleteFoodTransaction(FoodTransactionRequest transaction) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                foodTransactionRequestDao.deleteFoodTransaction(transaction);
            }
        });
    }

    public void deleteAllFoodTransactions() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                foodTransactionRequestDao.deleteAllFoodTransactions();
            }
        });
    }

    public LiveData<List<FoodTransactionRequest>> getTransactions() {
        return transactions;
    }

    public void insertFoodTransactionHistory(FoodTransactionHistory transaction) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                foodTransactionHistoryDao.insertFoodTransaction(transaction);
            }
        });
    }

    public void updateFoodTransactionHistory(FoodTransactionHistory transaction) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                foodTransactionHistoryDao.updateFoodTransaction(transaction);
            }
        });
    }

    public void deleteFoodTransactionHistory(FoodTransactionHistory transaction) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                foodTransactionHistoryDao.deleteFoodTransaction(transaction);
            }
        });
    }

    public void deleteAllFoodTransactionsHistory() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                foodTransactionHistoryDao.deleteAllFoodTransactions();
            }
        });
    }

    public LiveData<List<FoodTransactionHistory>> getTransactionsHistory() {
        return transactionsHistory;
    }
}

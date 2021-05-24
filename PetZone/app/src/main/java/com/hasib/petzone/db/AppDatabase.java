package com.hasib.petzone.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.hasib.petzone.PetCategory;
import com.hasib.petzone.UserType;

@Database(entities = {User.class, Food.class, FoodTransactionRequest.class, FoodTransactionHistory.class}, version = 1, exportSchema = false)
@TypeConverters({FoodConverter.class, UserConverter.class, ListConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    private static final String TAG = AppDatabase.class.getSimpleName();
    private static final String DB_NAME = "foodZone";
    private static AppDatabase instance;
    private static final RoomDatabase.Callback roomCallBack = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            AppExecutors.getInstance().diskIO().execute(new PopulateDatabase(instance));
        }
    };

    public static synchronized AppDatabase getInstance(Context context) {
      if (instance == null) {
          instance = Room.databaseBuilder(context.getApplicationContext(),
                  AppDatabase.class, DB_NAME)
                  .fallbackToDestructiveMigration()
                  .addCallback(roomCallBack)
                  .build();
      }
      return instance;
    }

    public abstract UserDao userDao();

    public abstract FoodDao foodDao();

    public abstract FoodTransactionRequestDao foodTransactionDao();

    public abstract FoodTransactionHistoryDao foodTransactionHistoryDao();

    private static class PopulateDatabase implements Runnable {
        private final FoodDao foodDao;
        private final UserDao userDao;

        private PopulateDatabase(AppDatabase database) {
            foodDao = database.foodDao();
            userDao = database.userDao();
        }

        @Override
        public void run() {
            userDao.insertUser(new User("Hasib", "01821941015", UserType.CUSTOMER, "smalreal14@gmail.com", "12345678"));

            foodDao.insertFood(new Food("Lara Poultry", PetCategory.CAT, 5, 200, "lara", 0));
            foodDao.insertFood(new Food("Royal Canin(FIT)", PetCategory.CAT, 6, 300, "canin", 0));
            foodDao.insertFood(new Food("Royal Canin (kitten)", PetCategory.CAT, 7, 300, "canin_kitten", 0));
            foodDao.insertFood(new Food("Royal Canin (Beauty)", PetCategory.CAT, 8, 350, "canin_beauty", 0));
            foodDao.insertFood(new Food("Whiskas Chicken", PetCategory.CAT, 8, 400, "whiskas", 0));
            foodDao.insertFood(new Food("Royal Canin (Puppy)", PetCategory.DOG, 8, 400, "canin_puppy", 0));
            foodDao.insertFood(new Food("Royal Canin (Dog)", PetCategory.DOG, 8, 500, "canin_dog", 0));
            foodDao.insertFood(new Food("Smartheart (Chicken)", PetCategory.DOG, 8, 450, "smart_chicken", 0));
            foodDao.insertFood(new Food("Smartheart (Beef)", PetCategory.DOG, 8, 350, "smart_beef", 0));
            foodDao.insertFood(new Food("Smartheart (Wet food)", PetCategory.DOG, 8, 450, "smart_liver", 0));
        }
    }
}

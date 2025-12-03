package com.example.a338_project_2.Database;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.a338_project_2.Database.entities.Cart;
import com.example.a338_project_2.Database.entities.FoodMenu;
import com.example.a338_project_2.Database.entities.User;
import com.example.a338_project_2.MainActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Cart.class, FoodMenu.class}, version = 1, exportSchema = false)
public abstract class MenuDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "menu_database";
    public static final String MENU_TABLE = "menuTable";

    private static volatile MenuDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    @SuppressWarnings("deprecation")
    static MenuDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (MenuDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    MenuDatabase.class,DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            Log.i(MainActivity.TAG, "DATABASE CREATED!");
            databaseWriteExecutor.execute(() -> {
                UserDAO dao = INSTANCE.userDAO();
                dao.deleteAll();
                User admin = new User("admin1", "admin1");
                admin.setAdmin(true);
                dao.insert(admin);

                User testUser1 = new User("testuser1", "testuser1");
                dao.insert(testUser1);


            });
            databaseWriteExecutor.execute(() -> {
               MenuDAO dao = INSTANCE.menuDAO();
               dao.deleteAll();

                FoodMenu burgerItem = new FoodMenu("Burger", 7);
                dao.insert(burgerItem);

                FoodMenu friesItem = new FoodMenu("Fries", 4);
                dao.insert(friesItem);

                FoodMenu sodaItem = new FoodMenu("Soda", 2);
                dao.insert(sodaItem);
            });
        }
    };


    public abstract UserDAO userDAO();
    public abstract MenuDAO menuDAO();

    public abstract CartDAO cartDAO();
}

package com.example.a338_project_2.Database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.a338_project_2.Database.entities.User;
import com.example.a338_project_2.MainActivity;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MenuRepository {

    private final UserDAO userDAO;
    private final MenuDAO menuDAO;

    private static MenuRepository repository;

    public MenuRepository(Application application) {
        MenuDatabase db = MenuDatabase.getDatabase(application);
        this.userDAO = db.userDAO();
        this.menuDAO = db.menuDAO();
    }

    public static MenuRepository getRepository(Application application) {
        if(repository != null) {
            return repository;
        }
        Future<MenuRepository> future = MenuDatabase.databaseWriteExecutor.submit(
                new Callable<MenuRepository>() {
                    @Override
                    public MenuRepository call() throws Exception {
                        return new MenuRepository(application);
                    }
                }
        );

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.d(MainActivity.TAG, "Problem getting GymLogRepository, thread error.");
        }
        return null;
    }

    public void insertUser(User... user) {
        MenuDatabase.databaseWriteExecutor.execute(() ->
        {
            userDAO.insert(user);
        });
    }

    public LiveData<User> getUserByUserName(String username) {
        return userDAO.getUserbyUserName(username);
    }

    public LiveData<User> getUserByUserId(int userId) {
        return userDAO.getUserbyUserId(userId);
    }
}

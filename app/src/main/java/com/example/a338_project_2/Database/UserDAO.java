package com.example.a338_project_2.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.a338_project_2.Database.entities.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User... user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + UserDatabase.USER_TABLE + " ORDER BY username")
    LiveData<List<User>> getALlUser();

    @Query("DELETE from " + UserDatabase.USER_TABLE)
    void deleteAll();

    @Query("SELECT * from " + UserDatabase.USER_TABLE + " WHERE username == :username")
    LiveData<User> getUserbyUserName(String username);

    @Query("SELECT * from " + UserDatabase.USER_TABLE + " WHERE id == :userId")
    LiveData<User> getUserbyUserId(int userId);
}
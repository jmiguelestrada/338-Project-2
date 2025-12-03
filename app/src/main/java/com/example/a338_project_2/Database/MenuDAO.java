package com.example.a338_project_2.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.a338_project_2.Database.entities.Menu;

import java.util.List;

@Dao
public interface MenuDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Menu... menu);

    @Delete
    void delete(Menu menu);

    @Query("SELECT * FROM " + MenuDatabase.MENU_TABLE + " ORDER BY foodName")
    LiveData<List<Menu>> getAllMenuItems();

    @Query("DELETE from " + MenuDatabase.MENU_TABLE)
    void deleteAll();

    @Query("SELECT * from " + MenuDatabase.MENU_TABLE + " WHERE foodName == :foodName")
    LiveData<Menu> getMenuByUserName(String foodName);

    @Query("SELECT * from " + MenuDatabase.MENU_TABLE + " WHERE id == :menuItemId")
    LiveData<Menu> getMenuByUserId(int menuItemId);
}

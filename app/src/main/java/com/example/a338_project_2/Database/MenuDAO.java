package com.example.a338_project_2.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.a338_project_2.Database.entities.FoodMenu;

import java.util.List;

@Dao
public interface MenuDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FoodMenu... menu);

    @Delete
    void delete(FoodMenu menu);

    @Query("SELECT * FROM " + MenuDatabase.MENU_TABLE + " ORDER BY foodName")
    LiveData<List<FoodMenu>> getAllMenuItems();

    @Query("DELETE from " + MenuDatabase.MENU_TABLE)
    void deleteAll();

    @Query("SELECT * from " + MenuDatabase.MENU_TABLE + " WHERE foodName == :foodName")
    LiveData<FoodMenu> getMenuItemByName(String foodName);

    @Query("SELECT * from " + MenuDatabase.MENU_TABLE + " WHERE id == :menuItemId")
    LiveData<FoodMenu> getMenuById(int menuItemId);
}

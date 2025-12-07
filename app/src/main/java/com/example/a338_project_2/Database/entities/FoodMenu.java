package com.example.a338_project_2.Database.entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.a338_project_2.Database.MenuDatabase;

import java.util.Objects;


@Entity(tableName = MenuDatabase.MENU_TABLE)
public class FoodMenu {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String foodName;

    private int price;



    public FoodMenu(String foodName, int price) {
        this.foodName = foodName;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FoodMenu menu = (FoodMenu) o;
        return id == menu.id && price == menu.price && Objects.equals(foodName, menu.foodName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, foodName, price);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


}

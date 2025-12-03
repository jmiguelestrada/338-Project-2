package com.example.a338_project_2.Database.entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.a338_project_2.Database.MenuDatabase;

import java.util.Arrays;
import java.util.Objects;


@Entity(tableName = MenuDatabase.MENU_TABLE)
public class Menu {

    @PrimaryKey
    private int id;

    private String foodName;

    private int price;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    public Menu(String foodName, int price, byte[] image) {
        this.foodName = foodName;
        this.price = price;
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return id == menu.id && price == menu.price && Objects.equals(foodName, menu.foodName) && Objects.deepEquals(image, menu.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, foodName, price, Arrays.hashCode(image));
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}

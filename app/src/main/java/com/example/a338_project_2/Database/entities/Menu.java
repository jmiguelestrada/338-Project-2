package com.example.a338_project_2.Database.entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;


@Entity(tableName = "Menu")
public class Menu {

    @PrimaryKey
    private int id;

    private String foodName;

    private int price;

    private String img;

    public Menu(String foodName, int price, String img) {
        this.foodName = foodName;
        this.price = price;
        this.img = img;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return id == menu.id && price == menu.price && Objects.equals(foodName, menu.foodName) && Objects.equals(img, menu.img);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, foodName, price, img);
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}

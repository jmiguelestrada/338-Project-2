package com.example.a338_project_2.Database.entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "Menu")
public class Menu {

    @PrimaryKey
    private int id;

    private String foodName;

    private int price;

    private String img;

}

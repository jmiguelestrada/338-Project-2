package com.example.a338_project_2.Database.entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "receipts")
public class Receipt {

    @PrimaryKey
    private int id;

    private int userIdData;

    private int menuItemIdData;


}

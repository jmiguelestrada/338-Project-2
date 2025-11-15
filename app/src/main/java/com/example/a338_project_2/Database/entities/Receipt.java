package com.example.a338_project_2.Database.entities;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity (tableName = "receipts")
public class Receipt {

    @PrimaryKey
    private int id;

    private int userIdData;

    private String userName;

    private int menuItemIdData;


    private int menuItemPrice;

    private String menuItemName;

    public Receipt(int userIdData, String userName, int menuItemIdData, int menuItemPrice, String menuItemName) {
        this.userIdData = userIdData;
        this.userName = userName;
        this.menuItemIdData = menuItemIdData;
        this.menuItemPrice = menuItemPrice;
        this.menuItemName = menuItemName;
    }

    @NonNull
    @Override
    public String toString() {
        return  "=-=-=-=-=-=-==-=-=-=-=-=-=\n" +
                "Order: " + id + '\n' +
                "Customer Name:" + userName + '\n' +
                "items: "+ menuItemName /*TODO: probably use a foreach to print all items make the database receive an array or hashmap idk */+ "  .............  " + menuItemPrice+'\n' +
                "total:" + menuItemPrice + '\n' +
                "=-=-=-=-=-=-==-=-=-=-=-=-=\n";
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Receipt receipt = (Receipt) o;
        return id == receipt.id && userIdData == receipt.userIdData && menuItemIdData == receipt.menuItemIdData && menuItemPrice == receipt.menuItemPrice && Objects.equals(userName, receipt.userName) && Objects.equals(menuItemName, receipt.menuItemName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userIdData, userName, menuItemIdData, menuItemPrice, menuItemName);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserIdData() {
        return userIdData;
    }

    public void setUserIdData(int userIdData) {
        this.userIdData = userIdData;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getMenuItemIdData() {
        return menuItemIdData;
    }

    public void setMenuItemIdData(int menuItemIdData) {
        this.menuItemIdData = menuItemIdData;
    }

    public int getMenuItemPrice() {
        return menuItemPrice;
    }

    public void setMenuItemPrice(int menuItemPrice) {
        this.menuItemPrice = menuItemPrice;
    }

    public String getMenuItemName() {
        return menuItemName;
    }

    public void setMenuItemName(String menuItemName) {
        this.menuItemName = menuItemName;
    }
}

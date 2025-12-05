package com.example.a338_project_2.Database.entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "Menu")
public class Cart {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int userId;

    private int menuItemId;

    private int menuItemQuantity;

    private String menuItemName;

    private int menuItemPrice;

    private int total;

    public Cart(int userId, int menuItemId, String menuItemName, int menuItemPrice, int menuItemQuantity)
    {
        this.userId = userId;
        this.menuItemId = menuItemId;
        this.menuItemName = menuItemName;
        this.menuItemPrice = menuItemPrice;
        this.menuItemQuantity = menuItemQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return id == cart.id && menuItemId == cart.menuItemId && menuItemQuantity == cart.menuItemQuantity && menuItemName == cart.menuItemName && menuItemPrice == cart.menuItemPrice && total == cart.total;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, menuItemId, menuItemQuantity, menuItemName, menuItemPrice, total);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public int getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(int menuItemId) {
        this.menuItemId = menuItemId;
    }

    public int getMenuItemQuantity() {
        return menuItemQuantity;
    }

    public void setMenuItemQuantity(int menuItemQuantity) {
        this.menuItemQuantity = menuItemQuantity;
    }

    public String getMenuItemName() {
        return menuItemName;
    }

    public void setMenuItemName(String menuItemName) {
        this.menuItemName = menuItemName;
    }

    public int getMenuItemPrice() {
        return menuItemPrice;
    }

    public void setMenuItemPrice(int menuItemPrice) {
        this.menuItemPrice = menuItemPrice;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}

package com.example.a338_project_2.Database.entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "Menu")
public class Cart {

    @PrimaryKey
    private int id;

    private int menuItemId;

    private int menuItemQuantity;

    private int menuItemName;

    private int menuItemPrice;

    private int total;

    public Cart(int menuItemId, int menuItemQuantity, int menuItemName, int menuItemPrice, int total) {
        this.menuItemId = menuItemId;
        this.menuItemQuantity = menuItemQuantity;
        this.menuItemName = menuItemName;
        this.menuItemPrice = menuItemPrice;
        this.total = total;
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

    public int getMenuItemName() {
        return menuItemName;
    }

    public void setMenuItemName(int menuItemName) {
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

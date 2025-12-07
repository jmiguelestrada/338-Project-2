package com.example.a338_project_2;

import static org.junit.Assert.*;

import com.example.a338_project_2.Database.entities.Cart;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class CartTotalTest {

    private int calculateTotal(List<Cart> items) {
        int total = 0;
        for (Cart c : items) {
            total += c.getMenuItemPrice() * c.getMenuItemQuantity();
        }
        return total;
    }

    @Test
    public void totalPrice() {

        Cart burger = new Cart(1, 101, "Burger", 7, 2);
        Cart fries  = new Cart(1, 102, "Fries", 4, 1);
        Cart soda   = new Cart(1, 103, "Soda", 2, 3);

        List<Cart> items = Arrays.asList(burger, fries, soda);

        int total = calculateTotal(items);

        assertEquals(24, total);
    }
}

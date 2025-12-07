package com.example.a338_project_2;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.a338_project_2.Database.CartDAO;
import com.example.a338_project_2.Database.MenuDatabase;
import com.example.a338_project_2.Database.entities.Cart;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class CartDaoTest {

    private MenuDatabase db;
    private CartDAO cartDao;
    private int userId = 1;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();

        db = Room.inMemoryDatabaseBuilder(context, MenuDatabase.class)
                .allowMainThreadQueries()
                .build();

        cartDao = db.cartDAO();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void insertAndRetrieveCartItems() {
        Cart burger = new Cart(userId, 101, "Burger", 7, 2);
        Cart fries  = new Cart(userId, 102, "Fries", 4, 1);

        cartDao.insert(burger);
        cartDao.insert(fries);

        List<Cart> items = cartDao.getAllCartItemsForUser(userId);

        assertEquals(2, items.size());

        Cart first = items.get(0);
        assertEquals("Burger", first.getMenuItemName());
        assertEquals(2, first.getMenuItemQuantity());

        Cart second = items.get(1);
        assertEquals("Fries", second.getMenuItemName());
        assertEquals(1, second.getMenuItemQuantity());
    }
}

package com.example.a338_project_2.Database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.example.a338_project_2.Database.entities.Cart;


import java.util.List;


@Dao
public interface CartDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Cart cart);

    @Update
    void update(Cart cart);

    @Delete
    void delete(Cart cart);

    @Query("DELETE FROM Menu")
    void clearCart();

    @Query("SELECT * FROM Menu")
    List<Cart> getAllCartItems();

    @Query("SELECT * FROM Menu WHERE menuItemId = :menuItemId LIMIT 1")
    Cart getCartItemByMenuId(int menuItemId);

    @Query("SELECT SUM(menuItemQuantity) FROM Menu")
    int getTotalItemCount();

}




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

    @Query("DELETE FROM Cart")
    void clearCart();

    @Query("SELECT * FROM Cart")
    List<Cart> getAllCartItems();

    @Query("SELECT * FROM Cart WHERE menuItemId = :menuItemId LIMIT 1")
    Cart getCartItemByMenuId(int menuItemId);

    @Query("SELECT SUM(menuItemQuantity) FROM Cart")
    int getTotalItemCount();


    @Query("DELETE FROM Cart WHERE id = :cartId")
    void deleteById(int cartId);
}




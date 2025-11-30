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


    // Insert a new item
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Cart cart);


    // Update quantity or name/price
    @Update
    void update(Cart cart);


    // Delete a specific cart item
    @Delete
    void delete(Cart cart);


    // Delete all cart items (checkout or clear cart)
    @Query("DELETE FROM Cart")
    void clearCart();


    // Get all items currently in the cart
    @Query("SELECT * FROM Cart")
    List<Cart> getAllCartItems();


    // Get a specific cart item (for add-to-cart logic)
    @Query("SELECT * FROM Cart WHERE menuItemId = :menuItemId LIMIT 1")
    Cart getCartItemByMenuId(int menuItemId);


    // Get total number of items (sum of quantities)
    @Query("SELECT SUM(menuItemQuantity) FROM Cart")
    int getTotalItemCount();


    // Optional: delete by ID
    @Query("DELETE FROM Cart WHERE id = :cartId")
    void deleteById(int cartId);
}




package com.example.a338_project_2;


import android.os.Bundle;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.a338_project_2.Database.CartDAO;
import com.example.a338_project_2.Database.MenuDatabase;
import com.example.a338_project_2.Database.entities.Cart;


import java.util.ArrayList;
import java.util.List;


public class CartActivity extends AppCompatActivity {


    private RecyclerView cartRecyclerView;
    private TextView totalPriceTextView;


    private CartAdapter cartAdapter;
    private CartDAO cartDAO;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        totalPriceTextView = findViewById(R.id.totalPriceTextView);

        cartDAO = MenuDatabase.getDatabase(getApplicationContext()).cartDAO();

        cartAdapter = new CartAdapter(new ArrayList<>());
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartRecyclerView.setAdapter(cartAdapter);

        loadCartItems();
    }


    @Override
    protected void onResume() {
        super.onResume();
        // Refresh in case cart changed
        loadCartItems();
    }


    private void loadCartItems() {
        MenuDatabase.databaseWriteExecutor.execute(() -> {
            List<Cart> cartItems = cartDAO.getAllCartItems();


            double total = 0.0;
            for (Cart item : cartItems) {
                total += item.getMenuItemPrice() * item.getMenuItemQuantity();
            }


            double finalTotal = total;
            runOnUiThread(() -> {
                cartAdapter.setItems(cartItems);
                totalPriceTextView.setText(String.format("Total: $%.2f", finalTotal));
            });
        });
    }

    public static android.content.Intent cartIntentFactory(android.content.Context context) {
        return new android.content.Intent(context, CartActivity.class);
    }
}

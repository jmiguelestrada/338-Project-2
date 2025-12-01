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

    private CartDAO cartDAO;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
    }

    public static android.content.Intent cartIntentFactory(android.content.Context context) {
        return new android.content.Intent(context, CartActivity.class);
    }
}

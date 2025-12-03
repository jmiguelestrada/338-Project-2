package com.example.a338_project_2;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.a338_project_2.Database.CartDAO;
import com.example.a338_project_2.Database.MenuDatabase;
import com.example.a338_project_2.Database.entities.Cart;
import com.example.a338_project_2.Database.entities.FoodMenu;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CartActivity extends AppCompatActivity {

    private static final String MAIN_ACTIVITY_USER_ID = "MAIN_ACTIVITY_USER_ID";
    @SuppressWarnings("UnusedDeclaration")
    static final String SHARED_PREFERENCE_USERID_KEY = "SHARED_PREFERENCE_USERID_KEY";

    static final String SAVED_INSTANCE_STATE_USERID_KEY = "SAVED_INSTANCE_STATE_USERID_KEY";
    private static final int LOGGED_OUT = -1;

    private static HashMap<LiveData<FoodMenu>, Integer> cartUserOrder = new HashMap<>();

    private CartDAO cartDAO;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
    }


    static Intent cartActivityIntentFactory(Context context, int userId, HashMap<LiveData<FoodMenu>, Integer> userOrder) {
        Intent intent = new Intent(context, CartActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        cartUserOrder = userOrder;
        return intent;
    }
}

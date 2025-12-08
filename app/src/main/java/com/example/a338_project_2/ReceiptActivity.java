package com.example.a338_project_2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.a338_project_2.Database.CartDAO;
import com.example.a338_project_2.Database.MenuDatabase;
import com.example.a338_project_2.Database.MenuRepository;
import com.example.a338_project_2.Database.entities.Cart;
import com.example.a338_project_2.Database.entities.User;
import com.example.a338_project_2.databinding.ActivityReceiptBinding;

import java.util.List;

public class ReceiptActivity extends AppCompatActivity {
    private static final String MAIN_ACTIVITY_USER_ID = "MAIN_ACTIVITY_USER_ID";
    @SuppressWarnings("UnusedDeclaration")
    static final String SHARED_PREFERENCE_USERID_KEY = "SHARED_PREFERENCE_USERID_KEY";

    static final String SAVED_INSTANCE_STATE_USERID_KEY = "SAVED_INSTANCE_STATE_USERID_KEY";

    static final String SHARED_PREFERENCE_USERID_VALUE = "SHARED_PREFERENCE_USERID_VALUE";
    private static final int LOGGED_OUT = -1;

    private ActivityReceiptBinding binding;
    private int loggedInUserId = LOGGED_OUT;

    private static int receiptIdPlaceHolder = 0;

    private MenuRepository repository;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReceiptBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        repository = MenuRepository.getRepository(getApplication());
        loginUser(savedInstanceState);

        loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);

        if (loggedInUserId == LOGGED_OUT) {
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(
                    getString(R.string.preference_file_key),
                    Context.MODE_PRIVATE
            );
            loggedInUserId = sharedPreferences.getInt(getString(R.string.preference_userId_key), LOGGED_OUT);
        }

        if (loggedInUserId == LOGGED_OUT) {
            Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
            startActivity(intent);
            finish();
            return;
        }

        loadReceiptFromDatabase();

        binding.buttonOrderAgain.setOnClickListener(v -> {
            orderAgainButtonLogic();
        });

        binding.buttonLogout.setOnClickListener(v -> {
            logoutButtonLogic();
        });
    }

    private void clearCartFromDatabase(){
        MenuDatabase.databaseWriteExecutor.execute(() -> {
            CartDAO cartDAO = MenuDatabase.getDatabase(getApplicationContext()).cartDAO();
            cartDAO.clearCartForUser(loggedInUserId);
        });
    }


    private void loadReceiptFromDatabase() {
        MenuDatabase.databaseWriteExecutor.execute(() -> {
            CartDAO cartDAO = MenuDatabase.getDatabase(getApplicationContext()).cartDAO();
            List<Cart> items = cartDAO.getAllCartItemsForUser(loggedInUserId);

            runOnUiThread(() -> createReceipt(items));
        });
    }

    private void createReceipt(List<Cart> items){
        receiptIdPlaceHolder++;
        StringBuilder itemsText = new StringBuilder();
        int total = 0;
        itemsText.append("=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=\n\n" +
                "Order: " + receiptIdPlaceHolder + "\n" + '\n' +
                "Customer Name:    " + user.getUsername() + "\n\n\n" +
                "items: \n");
        for (Cart item : items) {
            int lineTotal = item.getMenuItemPrice() * item.getMenuItemQuantity();
            total += lineTotal;

            itemsText.append(item.getMenuItemName()  + "  .............  Price:  " + item.getMenuItemPrice() + " X Count: " + item.getMenuItemQuantity() + '\n');
        }
        itemsText.append("\n\ntotal:" + total + '\n' +
                "=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=\n");

        binding.receiptInfoTextView.setText(itemsText);
    }

    private void logoutButtonLogic(){
        loggedInUserId = LOGGED_OUT;
        updateSharedPreference();
        Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
        startActivity(intent);
        clearCartFromDatabase();


        finish();
    }

    private void orderAgainButtonLogic(){
            clearCartFromDatabase();
            Intent intent = LandingPageActivity.landingPageIntentFactory(this, loggedInUserId);
            startActivity(intent);

    }


    private void updateSharedPreference(){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(getString(R.string.preference_userId_key), loggedInUserId);
        sharedPrefEditor.apply();
    }

    private void loginUser(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);

        loggedInUserId = sharedPreferences.getInt(getString(R.string.preference_userId_key), LOGGED_OUT);

        if(loggedInUserId == LOGGED_OUT & savedInstanceState != null && savedInstanceState.containsKey(SAVED_INSTANCE_STATE_USERID_KEY)){
            loggedInUserId = savedInstanceState.getInt(SAVED_INSTANCE_STATE_USERID_KEY, LOGGED_OUT);
        }

        if(loggedInUserId == LOGGED_OUT){
            loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);
        }

        if(loggedInUserId == LOGGED_OUT){
            return;
        }
        LiveData<User> userObserver = repository.getUserByUserId(loggedInUserId);
        userObserver.observe(this, user -> {
            this.user = user;
            if(this.user != null){
                invalidateOptionsMenu();
            }
        });
    }


    static Intent receiptActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, ReceiptActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }
}
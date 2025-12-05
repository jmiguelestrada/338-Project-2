package com.example.a338_project_2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.a338_project_2.Database.MenuRepository;
import com.example.a338_project_2.Database.entities.User;
import com.example.a338_project_2.databinding.ActivityLandingPageBinding;
import com.example.a338_project_2.databinding.ActivityMainBinding;
import com.example.a338_project_2.Database.entities.FoodMenu;

import java.util.HashMap;

public class LandingPageActivity extends AppCompatActivity {

    private static final String MAIN_ACTIVITY_USER_ID = "MAIN_ACTIVITY_USER_ID";
    @SuppressWarnings("UnusedDeclaration")
    static final String SHARED_PREFERENCE_USERID_KEY = "SHARED_PREFERENCE_USERID_KEY";

    static final String SAVED_INSTANCE_STATE_USERID_KEY = "SAVED_INSTANCE_STATE_USERID_KEY";
    private static final int LOGGED_OUT = -1;
    private ActivityLandingPageBinding binding;
    private MenuRepository repository;
    public static final String  TAG = "DAC_MENU";
    private int loggedInUserId = LOGGED_OUT;
    private User user;

    private LiveData<FoodMenu> burgerLiveData;
    private LiveData<FoodMenu> friesLiveData;
    private LiveData<FoodMenu> sodaLiveData;

    private FoodMenu burgerFood;
    private FoodMenu friesFood;
    private FoodMenu sodaFood;

    private int burgerCount = 0;
    private int sodaCount = 0;
    private int friesCount = 0;

    private HashMap<FoodMenu, Integer> userOrder = new HashMap<>();

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityLandingPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = MenuRepository.getRepository(getApplication());
        loginUser(savedInstanceState);

        if(loggedInUserId == -1){
            Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
            startActivity(intent);
        }
        updateSharedPreference();

        burgerLiveData = repository.getMenuItemByName("Burger");
        friesLiveData  = repository.getMenuItemByName("Fries");
        sodaLiveData   = repository.getMenuItemByName("Soda");

        burgerLiveData.observe(this, food -> burgerFood = food);
        friesLiveData.observe(this,  food -> friesFood  = food);
        sodaLiveData.observe(this,   food -> sodaFood   = food);

        /*
         * Code for the Buttons to add and sub
         */

        /**
         * Burger
         */
        binding.button6.setOnClickListener(v->{
            burgerCount++;
            binding.burgerCount.setText(String.valueOf(burgerCount));
        });


        binding.button.setOnClickListener(v -> {
            if (burgerCount > 0) burgerCount--;
            binding.burgerCount.setText(String.valueOf(burgerCount));
        });

        /**
         * Soda
         */
        binding.button3.setOnClickListener(v->{
            sodaCount++;
            binding.sodaCount.setText(String.valueOf(sodaCount));
        });

        binding.button5.setOnClickListener(v->{
            if(sodaCount>0) sodaCount--;
            binding.sodaCount.setText(String.valueOf(sodaCount));
        });


        /**
         * Fries
         */

        binding.button2.setOnClickListener(v->{
            friesCount++;
            binding.friesCount.setText(String.valueOf(friesCount));
        });

        binding.button4.setOnClickListener(v->{
            if(friesCount>0) friesCount--;
            binding.friesCount.setText(String.valueOf(friesCount));
        });


        binding.userCartButton.setOnClickListener(v -> {
            userOrder.clear();  // avoid stale data from previous visit

            // Use cached FoodMenu objects, not LiveData.getValue()
            if (burgerCount > 0 && burgerFood != null) {
                userOrder.put(burgerFood, burgerCount);
            }
            if (friesCount > 0 && friesFood != null) {
                userOrder.put(friesFood, friesCount);
            }
            if (sodaCount > 0 && sodaFood != null) {
                userOrder.put(sodaFood, sodaCount);
            }

            int totalItems = burgerCount + friesCount + sodaCount;

            if (!userOrder.isEmpty() && totalItems > 0 && user != null) {
                startActivity(
                        CartActivity.cartActivityIntentFactory(
                                this,
                                user.getId(),
                                userOrder
                        )
                );
            } else {
                Toast.makeText(this, "Please order something bro...", Toast.LENGTH_SHORT).show();
            }
        });


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);

        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if (!user.isAdmin()){
            binding.adminSettingsButton.setVisibility(View.INVISIBLE);
        }

        MenuItem item = menu.findItem(R.id.logoutMenuItem);
        item.setVisible(true);
        if(user == null){
            return false;
        }
        item.setTitle(user.getUsername());
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {

                showLogoutDialog();
                return false;
            }
        });
        return true;
    }

    private void showLogoutDialog(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(LandingPageActivity.this);
        final AlertDialog alertDialog = alertBuilder.create();

        alertBuilder.setMessage("Logout?");

        alertBuilder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                logout();
            }
        });

        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });

        alertBuilder.create().show();

    }

    private void logout() {

        loggedInUserId = LOGGED_OUT;
        updateSharedPreference();
        getIntent().putExtra(MAIN_ACTIVITY_USER_ID,LOGGED_OUT);

        startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_INSTANCE_STATE_USERID_KEY,loggedInUserId);
        updateSharedPreference();
    }

    private void updateSharedPreference(){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(getString(R.string.preference_userId_key), loggedInUserId);
        sharedPrefEditor.apply();
    }

    static Intent landingPageIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, LandingPageActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }
}
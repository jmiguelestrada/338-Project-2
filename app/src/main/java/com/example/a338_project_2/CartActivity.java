package com.example.a338_project_2;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;


import com.example.a338_project_2.Database.CartDAO;
import com.example.a338_project_2.Database.MenuDatabase;
import com.example.a338_project_2.Database.MenuRepository;
import com.example.a338_project_2.Database.entities.Cart;
import com.example.a338_project_2.Database.entities.FoodMenu;
import com.example.a338_project_2.Database.entities.User;
import com.example.a338_project_2.databinding.ActivityCartBinding;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CartActivity extends AppCompatActivity {

    private static final String MAIN_ACTIVITY_USER_ID = "MAIN_ACTIVITY_USER_ID";
    @SuppressWarnings("UnusedDeclaration")
    static final String SHARED_PREFERENCE_USERID_KEY = "SHARED_PREFERENCE_USERID_KEY";

    static final String SAVED_INSTANCE_STATE_USERID_KEY = "SAVED_INSTANCE_STATE_USERID_KEY";

    static final String SHARED_PREFERENCE_USERID_VALUE = "SHARED_PREFERENCE_USERID_VALUE";
    private static final int LOGGED_OUT = -1;

    private ActivityCartBinding binding;
    private int loggedInUserId = LOGGED_OUT;

    private MenuRepository repository;

    private User user;

    private static HashMap<FoodMenu, Integer> cartUserOrder = new HashMap<>();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
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

        loadCartFromDatabase();
        binding.checkoutButton.setOnClickListener(v ->
                Toast.makeText(this, "Order placed!", Toast.LENGTH_SHORT).show()
        );
    }


    private void loadCartFromDatabase() {
        MenuDatabase.databaseWriteExecutor.execute(() -> {
            CartDAO cartDAO = MenuDatabase.getDatabase(getApplicationContext()).cartDAO();
            List<Cart> items = cartDAO.getAllCartItemsForUser(loggedInUserId);

            runOnUiThread(() -> updateCartDisplay(items));
        });
    }

    private void updateCartDisplay(List<Cart> items) {
        StringBuilder itemsText = new StringBuilder();
        int total = 0;

        for (Cart item : items) {
            int lineTotal = item.getMenuItemPrice() * item.getMenuItemQuantity();
            total += lineTotal;

            itemsText.append(item.getMenuItemName())
                    .append(" × ")
                    .append(item.getMenuItemQuantity())
                    .append(" = $")
                    .append(lineTotal)
                    .append("\n");
        }

        binding.cartOrderTextView.setText(itemsText.toString());
        binding.totalPriceTextView.setText(
                String.format("Total: $%.2f", (double) total)
        );
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
        MenuItem item = menu.findItem(R.id.logoutMenuItem);
        item.setVisible(true);

        if (user == null) {
            return false;
        }

        item.setTitle(user.getUsername());
        item.setOnMenuItemClickListener(menuItem -> {
            showLogoutDialog();
            return false;
        });

        return true;
    }

    private void showLogoutDialog(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(CartActivity.this);
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

    private void updateSharedPreference(){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(getString(R.string.preference_userId_key), loggedInUserId);
        sharedPrefEditor.apply();
    }

    static Intent cartActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, CartActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }
}

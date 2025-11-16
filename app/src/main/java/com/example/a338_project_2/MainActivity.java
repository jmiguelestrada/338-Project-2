package com.example.a338_project_2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a338_project_2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "DAC_MENU";
    private static final String MAIN_ACTIVITY_USER_ID = "MAIN_ACTIVITY_USER_ID";
    static final String SHARED_PREFERENCE_USERID_KEY   = "SHARED_PREFERENCE_USERID_KEY";
    static final String SHARED_PREFERENCE_USERID_VALUE = "SHARED_PREFERENCE_USERID_VALUE";
    private static final int LOGGED_OUT = -1;

    private ActivityMainBinding binding;
    private int loggedInUserId = LOGGED_OUT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        handleLoginOrSession();

        if (loggedInUserId == LOGGED_OUT) {
            setupLoggedOutUi();
        }

        binding.mainActivityLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    private void handleLoginOrSession() {

        SharedPreferences sp = getSharedPreferences(SHARED_PREFERENCE_USERID_KEY, Context.MODE_PRIVATE);
        loggedInUserId = sp.getInt(SHARED_PREFERENCE_USERID_VALUE, LOGGED_OUT);

        if (loggedInUserId == LOGGED_OUT) {
            loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);
            if (loggedInUserId != LOGGED_OUT) {
                sp.edit().putInt(SHARED_PREFERENCE_USERID_VALUE, loggedInUserId).apply();
            }
        }
        if (loggedInUserId != LOGGED_OUT) {
            goToLanding();
        }
    }

    private void setupLoggedOutUi() {
        binding.mainActivityLoginButton.setOnClickListener(v ->
                startActivity(LoginActivity.loginIntentFactory(getApplicationContext())));

        binding.mainActivitySignupButton.setOnClickListener(v ->
                startActivity(LoginActivity.loginIntentFactory(getApplicationContext())));
    }

    private void goToLanding() {
        startActivity(new Intent(this, LandingPageActivity.class));
        finish();
    }

    static Intent mainActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }
}

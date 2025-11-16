package com.example.a338_project_2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class LandingPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_landing_page);

    }

    static Intent landingPageIntentFactory(Context context) {
        return new Intent(context, LandingPageActivity.class);
    }
}
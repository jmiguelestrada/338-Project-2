package com.example.a338_project_2;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Button;

import com.example.a338_project_2.Database.MenuRepository;
import com.example.a338_project_2.Database.entities.User;

import java.util.List;

public class AdminActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity);

        // Back button
        Button adminBack = findViewById(R.id.BackButton);
        adminBack.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, LandingPageActivity.class);
            startActivity(intent);
            finish();
        });


    }
}

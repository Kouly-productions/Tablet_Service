package com.example.tabletservice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UserLoggedIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_logged_in);

        Button btnloanTablet = findViewById(R.id.btn_tablet);
        Button btnHistoryTablet = findViewById(R.id.btn_history);

        btnloanTablet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserLoggedIn.this, LoanTabletForm.class);
                startActivity(intent);
            }
        });

        btnHistoryTablet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserLoggedIn.this, LoanHistory.class);
                startActivity(intent);
            }
        });
    }
}
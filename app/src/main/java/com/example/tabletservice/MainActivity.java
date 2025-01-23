package com.example.tabletservice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout selectionLayout = findViewById(R.id.selection_layout);
        LinearLayout userLoginLayout = findViewById(R.id.user_login_layout);
        LinearLayout adminLoginLayout = findViewById(R.id.admin_login_layout);

        Button btnUser = findViewById(R.id.btn_user);
        Button btnAdmin = findViewById(R.id.btn_admin);
        Button btnUserLogin = findViewById(R.id.btn_user_login);
        Button btnAdminLogin = findViewById(R.id.btn_admin_login);

        EditText userUsername = findViewById(R.id.user_username);
        EditText adminPassword = findViewById(R.id.admin_password);


        // On click show appropiate login screen
        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectionLayout.setVisibility(View.GONE);
                userLoginLayout.setVisibility(View.VISIBLE);
            }
        });

        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectionLayout.setVisibility(View.GONE);
                adminLoginLayout.setVisibility(View.VISIBLE);
            }
        });

        btnUserLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = userUsername.getText().toString();
                if (username.equalsIgnoreCase("Edward"))
                {
                    Intent intent = new Intent(MainActivity.this, UserLoggedIn.class);
                    startActivity(intent);
                }
            }
        });

        btnAdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = adminPassword.getText().toString();
                if (username.equalsIgnoreCase("123"))
                {
                    Intent intent = new Intent(MainActivity.this, AdminLoggedIn.class);
                    startActivity(intent);
                }
            }
        });
    }
}
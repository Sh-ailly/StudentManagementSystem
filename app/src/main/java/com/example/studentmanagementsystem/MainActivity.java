package com.example.studentmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.studentmanagementsystem.ui.main.Contact_Admin_Page;

import java.sql.Connection;

public class MainActivity extends AppCompatActivity {
    ConnectionClass connectionClass;
    Connection conn;
    Button login;
    TextView contact_admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        connectionClass = new ConnectionClass();
        conn = connectionClass.CONN(); // Establish the database connection

        login = findViewById(R.id.button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if (conn != null) {
                    Intent intent = new Intent(MainActivity.this, Dashboard.class);
                    startActivity(intent);
                //} else {
                    // Show a user-friendly message if connection fails
                    // For example, you can display a Toast
                   // Log.e("ERROR", "Failed to connect to database");
                //}
            }
        });

        contact_admin = findViewById(R.id.textView6);
        contact_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Contact_Admin_Page.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close the database connection when the activity is destroyed
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                Log.e("ERROR", "Error closing database connection: " + e.getMessage());
            }
        }
    }
}

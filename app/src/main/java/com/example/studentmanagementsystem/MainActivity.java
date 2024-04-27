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
import android.widget.Toast;

import com.example.studentmanagementsystem.ui.main.Contact_Admin_Page;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    ConnectionClass connectionClass;
    Connection con;
    ResultSet rs;
    String str;
    Button login;
    TextView contact_admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        connectionClass = new ConnectionClass(); // Assuming this class handles database connections

        login = findViewById(R.id.button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connect();
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

    public void connect() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                con = connectionClass.CONN(); // Assuming this method establishes the database connection

                if (con == null) {
                    str = "Error in connection";
                } else {
                    str = "Connection success";
                    // Start the Dashboard activity upon successful connection
                    Intent intent = new Intent(MainActivity.this, Dashboard.class);
                    startActivity(intent);
                }
            } catch (Exception e) {
                // Log the exception for debugging purposes
                Log.e("Database Connection", "Error connecting to database", e);
                str = "Error in connection";
            }
            runOnUiThread(() -> {
                Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
            });
        });
    }
}

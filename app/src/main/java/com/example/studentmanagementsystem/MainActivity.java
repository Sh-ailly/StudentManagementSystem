package com.example.studentmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentmanagementsystem.ContactAdminPageActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    ConnectionClass connectionClass;
    Connection con;
    ResultSet rs;
    String name, str;
    Button login;
    TextView contact_admin;
    EditText username, password;
    public String user;

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
                Intent intent = new Intent(MainActivity.this, ContactAdminPageActivity.class);
                startActivity(intent);
            }
        });
    }

    public void connect() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                con = connectionClass.CONN(); // Assuming this method establishes the database connection
                username=findViewById(R.id.editTextTextPersonName);
                password=findViewById(R.id.editTextTextPassword);
                user=username.getText().toString();
                String pass=password.getText().toString();
                if(validateLogin(user,pass))
                {
                    SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("user", user);
                    editor.apply();
                    // Start the Dashboard activity upon successful connection
                    Intent intent = new Intent(MainActivity.this, Dashboard.class);
                    startActivity(intent);
                    str="Valid Login";
                }
                else
                {
                    str="Invalid Login";
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
    private boolean validateLogin(String username, String password)
    {
        try{
            String query="SELECT * FROM login WHERE Roll_no=? AND Password=?";
            PreparedStatement statement=con.prepareStatement(query);
            statement.setString(1,username);
            statement.setString(2,password);
            rs= statement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
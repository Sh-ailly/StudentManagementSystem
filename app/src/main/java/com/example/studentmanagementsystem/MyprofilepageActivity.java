package com.example.studentmanagementsystem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyprofilepageActivity extends AppCompatActivity {
    MainActivity main;
    ImageView back;
    ConnectionClass connectionClass;
    Connection con;
    ResultSet rs;
    String str,name1;
    TextView name;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this is for hiding the action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        EdgeToEdge.enable(this);
        setContentView(R.layout.myprofilepage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        back=(ImageView)findViewById(R.id.profilebackbtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MyprofilepageActivity.this, Dashboard.class);
                startActivity(intent);
            }
        });
    }
    public void connect() {
        String user=main.user;
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                con = connectionClass.CONN(); // Assuming this method establishes the database connection
                String query = "SELECT First_name, Last_name FROM Student WHERE Roll_no=?";
                PreparedStatement statement = con.prepareStatement(query);
                statement.setString(1, user);
                rs = statement.executeQuery();
                if (rs.next()) {
                    String firstName = rs.getString("First_name");
                    String lastName = rs.getString("Last_name");
                    String fullName = firstName + " " + lastName;
                    runOnUiThread(() -> {
                        Toast.makeText(MyprofilepageActivity.this,fullName, Toast.LENGTH_SHORT).show(); // Set the full name in the UI thread
                    });
                    name=findViewById(R.id.textView9);
                    name.setText(fullName);
                }
            } catch (Exception e) {
                // Log the exception for debugging purposes
                Log.e("Database Connection", "Error connecting to database", e);
                str = "Error in connection";
            }
            runOnUiThread(() -> {
                Toast.makeText(MyprofilepageActivity.this,name1, Toast.LENGTH_SHORT).show();
            });
        });
    }
}
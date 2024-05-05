package com.example.studentmanagementsystem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
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
    String fullName;
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
        connectionClass = new ConnectionClass();

        connect();
    }
    public void connect() {
        main=new MainActivity();

        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String user = preferences.getString("user", "defaultUser");        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                con = connectionClass.CONN(); // Assuming this method establishes the database connection
                String query = "SELECT First_name, Last_name, Roll_no,DOB, Branch, Gender, Department, Phone_no, Email FROM Student WHERE Roll_no=?";
                PreparedStatement statement = con.prepareStatement(query);
                statement.setString(1, user);
                rs = statement.executeQuery();
                if (rs.next()) {
                    String firstName = rs.getString("First_name");
                    String lastName = rs.getString("Last_name");
                    fullName = firstName + " " + lastName;
                    TextView name = findViewById(R.id.textView9);
                    name.setText(fullName);
                    TextView roll= findViewById(R.id.textView21);
                    String rollno=rs.getString("Roll_no");
                    roll.setText(rollno);
                    TextView DOB=findViewById(R.id.textView36);
                    DOB.setText(rs.getString("DOB"));
                    TextView phone=findViewById(R.id.textView39);
                    phone.setText(rs.getString("Phone_no"));
                    TextView email=findViewById(R.id.textView41);
                    email.setText(rs.getString("Email"));
                    TextView Course=findViewById(R.id.textView10);
                    String main_course;
                    String department=rs.getString("Department");
                    String branch=rs.getString("Branch");
                    main_course=department+" "+branch;
                    Course.setText(main_course);
                    TextView gender=findViewById(R.id.textView33);
                    gender.setText(rs.getString("Gender"));
                   // runOnUiThread(() -> {
                    //    Toast.makeText(MyprofilepageActivity.this, fullName, Toast.LENGTH_SHORT).show();
                    //});
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(MyprofilepageActivity.this, "No data found for the user", Toast.LENGTH_SHORT).show();
                    });
                }
            } catch (Exception e) {
                // Log the exception for debugging purposes
                Log.e("Database Connection", "Error connecting to database", e);
                runOnUiThread(() -> {
                    Toast.makeText(MyprofilepageActivity.this, "Error in connection", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
}
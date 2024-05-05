package com.example.studentmanagementsystem;

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

public class FeesStructurepageActivity extends AppCompatActivity {
    ConnectionClass connectionClass;
    Connection con;
    ResultSet rs;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        EdgeToEdge.enable(this);
        setContentView(R.layout.fees_structurepage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize connectionClass object
        connectionClass = new ConnectionClass();

        back = findViewById(R.id.feebackbtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FeesStructurepageActivity.this, Dashboard.class);
                startActivity(intent);
            }
        });

        // Call the connect method after initialization
        connect();
    }

    public void connect() {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String user = preferences.getString("user", "defaultUser");

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                con = connectionClass.CONN(); // Assuming this method establishes the database connection
                String query = "SELECT Total_Fees, Paid_Fees, Unpaid_Fees FROM Student WHERE Roll_no=?";
                PreparedStatement statement = con.prepareStatement(query);
                statement.setString(1, user);
                rs = statement.executeQuery();
                if (rs.next()) {
                    String total = rs.getString("Total_Fees");
                    String paid = rs.getString("Paid_Fees");
                    String unpaid = rs.getString("Unpaid_Fees");

                    // Initialize UI elements before setting their text values
                    runOnUiThread(() -> {
                        TextView total_fees = findViewById(R.id.textView41);
                        TextView paid_fees = findViewById(R.id.textView42);
                        TextView unpaid_fees = findViewById(R.id.textView43);

                        // Set text values to the TextViews
                        total_fees.setText(total);
                        paid_fees.setText(paid);
                        unpaid_fees.setText(unpaid);
                    });
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(FeesStructurepageActivity.this, "No data found for the user", Toast.LENGTH_SHORT).show();
                    });
                }
            } catch (Exception e) {
                // Log the exception for debugging purposes
                Log.e("Database Connection", "Error connecting to database", e);
                runOnUiThread(() -> {
                    Toast.makeText(FeesStructurepageActivity.this, "Error in connection", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
}

package com.example.studentmanagementsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studentmanagementsystem.databinding.ActivityDashboardBinding;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Dashboard extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityDashboardBinding binding;
    Button profile;
    ConnectionClass connectionClass;
    Connection con;
    ResultSet rs;
    String fullName;

    TextView name, email;
    ImageView profileImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarDashboard.toolbar);
       /* binding.appBarDashboard.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.fab).show();
            }
        });*/
        profile = findViewById(R.id.nav_profile);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_dashboard);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id= menuItem.getItemId();
                if(id==R.id.nav_profile)
                {
                    Intent intent = new Intent(Dashboard.this, MyprofilepageActivity.class);
                    startActivity(intent);
                }
                else if(id==R.id.nav_fees)
                {
                    Intent intent = new Intent(Dashboard.this, FeesStructurepageActivity.class);
                    startActivity(intent);
                }
                else if(id==R.id.nav_assignment)
                {
                    Intent intent = new Intent(Dashboard.this, AssignmentPageActivity.class);
                    startActivity(intent);
                }
                else if(id==R.id.nav_grade)
                {
                    Intent intent = new Intent(Dashboard.this, GradepageActivity.class);
                    startActivity(intent);
                }
                else if(id==R.id.nav_attendance)
                {
                    Intent intent = new Intent(Dashboard.this, AttendancepageActivity.class);
                    startActivity(intent);
                }
                else if(id==R.id.nav_holidays)
                {
                    Intent intent = new Intent(Dashboard.this, HolidaypageActivity.class);
                    startActivity(intent);
                }
                return true;

            }
        });
        connectionClass = new ConnectionClass();
        connect();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_settings)
        {
            Intent intent = new Intent(Dashboard.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_dashboard);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public void connect() {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String user = preferences.getString("user", "defaultUser");        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                con = connectionClass.CONN(); // Assuming this method establishes the database connection
                String query = "SELECT First_name, Last_name, Email FROM Student WHERE Roll_no=?";
                PreparedStatement statement = con.prepareStatement(query);
                statement.setString(1, user);
                rs = statement.executeQuery();
                if (rs.next()) {
                    String firstName = rs.getString("First_name");
                    String lastName = rs.getString("Last_name");
                    String email_address=rs.getString("Email");
                    fullName = firstName + " " + lastName;
                    name = findViewById(R.id.textView1);
                    //name.setText(fullName);
                    email = findViewById(R.id.textView);
                    //email.setText(email_address);
                    profileImage=findViewById(R.id.imageView);
                    //byte[] imageBytes = rs.getBytes("Profile_Image");
                    //if (imageBytes != null) {
                      //  Bitmap bitmap = BitmapFactory.decodeStream(new ByteArrayInputStream(imageBytes));
                       // runOnUiThread(() -> profileImage.setImageBitmap(bitmap));
                    //}
                    //else {
                      //  runOnUiThread(() -> {
                        //    Toast.makeText(Dashboard.this, "No image found", Toast.LENGTH_SHORT).show();
                        //});
                   // }
                    runOnUiThread(() -> {
                        if (name != null) {
                            name.setText(fullName);
                        }
                        if (email != null) {
                            email.setText(email_address);
                        }
                    });

                    // runOnUiThread(() -> {
                    //    Toast.makeText(MyprofilepageActivity.this, fullName, Toast.LENGTH_SHORT).show();
                    //});
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(Dashboard.this, "No data found for the user", Toast.LENGTH_SHORT).show();
                    });
                }
            } catch (Exception e) {
                // Log the exception for debugging purposes
                Log.e("Database Connection", "Error connecting to database", e);
                runOnUiThread(() -> {
                    Toast.makeText(Dashboard.this, "Error in connection", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

}
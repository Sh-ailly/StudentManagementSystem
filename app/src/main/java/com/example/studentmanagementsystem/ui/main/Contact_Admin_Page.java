package com.example.studentmanagementsystem.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;

import com.example.studentmanagementsystem.MainActivity;
import com.example.studentmanagementsystem.R;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;

import java.util.Properties;

public class Contact_Admin_Page extends AppCompatActivity {
    ImageView back;
    Button button;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_contact_admin_page);
        back = (ImageView) findViewById(R.id.backbuttonimage);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Contact_Admin_Page.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
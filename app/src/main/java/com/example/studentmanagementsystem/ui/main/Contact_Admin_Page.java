package com.example.studentmanagementsystem.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.studentmanagementsystem.Dashboard;
import com.example.studentmanagementsystem.MainActivity;
import com.example.studentmanagementsystem.R;
import android.content.Intent;
import android.view.View;
import android.widget.*;

public class Contact_Admin_Page extends AppCompatActivity {
ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_admin_page);
        back=(ImageButton)findViewById(R.id.imageButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Contact_Admin_Page.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
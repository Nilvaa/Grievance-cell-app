package com.example.grievancecell.faculty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.grievancecell.Login;
import com.example.grievancecell.R;

public class Faculty_home extends AppCompatActivity {
    CardView grie,app;
    ImageView log,pro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_home);
        grie=findViewById(R.id.grie);
        app=findViewById(R.id.app);
        pro=findViewById(R.id.pro);
        log=findViewById(R.id.log);
        pro.setOnClickListener(v -> {
            Intent i=new Intent(getApplicationContext(), Faculty_profile.class);
            startActivity(i);
        });
        grie.setOnClickListener(v -> {
            Intent i=new Intent(getApplicationContext(), Faculty_view_grievance.class);
            startActivity(i);
        });
        app.setOnClickListener(v -> {
            Intent i=new Intent(getApplicationContext(), Faculty_view_appointment.class);
            startActivity(i);
        });
        log.setOnClickListener(v -> {
            Intent i=new Intent(getApplicationContext(), Login.class);
            startActivity(i);
        });

        log.setOnClickListener(v -> {
            Intent i=new Intent(getApplicationContext(), Login.class);
            startActivity(i);
        });

    }
    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Please logout", Toast.LENGTH_SHORT).show();
    }
}
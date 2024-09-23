package com.example.grievancecell.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.grievancecell.Login;
import com.example.grievancecell.R;
import com.example.grievancecell.faculty.Faculty_reg;
import com.example.grievancecell.student.Stud_reg;

public class Admin_home extends AppCompatActivity {
    CardView fac,stud,grie,act;
    ImageView log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        fac=findViewById(R.id.fac);
        act=findViewById(R.id.reply);
        stud=findViewById(R.id.stud);
        grie=findViewById(R.id.grie);
        log=findViewById(R.id.log);

        fac.setOnClickListener(v -> {
            Intent i= new Intent (getApplicationContext(), Admin_view_faculty.class);
            startActivity(i);
        });

        stud.setOnClickListener(v -> {
            Intent i= new Intent (getApplicationContext(), Admin_view_stud.class);
            startActivity(i);
        });
        grie.setOnClickListener(v -> {
            Intent i= new Intent (getApplicationContext(), Admin_view_grievance.class);
            startActivity(i);
        });
        act.setOnClickListener(v -> {
            Intent i= new Intent (getApplicationContext(), Admin_view_reply.class);
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
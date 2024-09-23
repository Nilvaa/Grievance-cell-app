package com.example.grievancecell;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;

import com.example.grievancecell.faculty.Faculty_reg;
import com.example.grievancecell.student.Stud_reg;

public class Options extends AppCompatActivity {
    CardView fac,stud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        fac=findViewById(R.id.fac);
        stud=findViewById(R.id.stud);

        fac.setOnClickListener(v -> {
            Intent i= new Intent (getApplicationContext(), Faculty_reg.class);
            startActivity(i);
        });

        stud.setOnClickListener(v -> {
            Intent i= new Intent (getApplicationContext(), Stud_reg.class);
            startActivity(i);
        });
    }
}
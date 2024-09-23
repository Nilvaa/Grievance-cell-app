package com.example.grievancecell.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.grievancecell.Login;
import com.example.grievancecell.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Student_home extends AppCompatActivity {

    AlertDialog.Builder builder;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;

                        int itemId = item.getItemId();

                        if (itemId == R.id.action_grie) {
                            selectedFragment = new GrievanceFragment();
                        } else if (itemId == R.id.action_mygrie) {
                            selectedFragment = new MyGrievanceFragment();
                        } else if (itemId == R.id.action_profile) {
                            selectedFragment = new ProfileFragment();
                        }else if (itemId == R.id.action_appoint) {
                            selectedFragment = new AppointmentsFragment();
                        }

                        if (selectedFragment != null) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.fragment_container, selectedFragment);
                            transaction.commit();
                        }

                        return true;
                    }
                });

        // Set the default fragment to MVDComplaintsFragment when the activity starts
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new MyGrievanceFragment())
                .commit();
    }

    @Override
    public void onBackPressed() {
        builder = new AlertDialog.Builder(Student_home.this);
        builder.setMessage("Logout!! Are You Sure?")
                .setCancelable(false)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Handle the logout action
                        startActivity(new Intent(getApplicationContext(), Login.class));
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Call the default behavior of the back button
//                        Admin_home.super.onBackPressed();
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();

        alert.setTitle("Logout");
        alert.show();
    }
}
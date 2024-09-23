package com.example.grievancecell.faculty;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.grievancecell.R;
import com.example.grievancecell.common.Utility;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Faculty_fix_appointment extends AppCompatActivity {
    EditText date,time,sentto;
    String a_id,uid,nam,ti,da;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_fix_appointment);
        date=findViewById(R.id.date);
        time=findViewById(R.id.time);
        sentto=findViewById(R.id.sent);
        btn=findViewById(R.id.btnreg);
        Intent i = getIntent();
        a_id = i.getExtras().getString("a_id");
        uid = i.getExtras().getString("uid");
        nam = i.getExtras().getString("nam");
        sentto.setText(nam);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                int year = mcurrentDate.get(Calendar.YEAR);
                int month = mcurrentDate.get(Calendar.MONTH);
                int day = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(
                        v.getContext(), // Use the context of the clicked view
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {
                                // Month is zero-based, so add 1 to the month value
                                selectedMonth += 1;

                                // Format the date in a user-friendly way
                                da = selectedMonth + "/" + selectedDay + "/" + selectedYear;
                                date.setText(da);
                            }
                        },
                        year, month, day
                );

                mDatePicker.setTitle("Select Date");

                // Show the date picker dialog
                mDatePicker.show();
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mCurrentTime = Calendar.getInstance();
                int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mCurrentTime.get(Calendar.MINUTE);

                // TimePickerDialog for selecting time
                TimePickerDialog mTimePicker = new TimePickerDialog(
                        v.getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                // Format the selected time
                                String amPm = (selectedHour < 12) ? "AM" : "PM";
                                selectedHour = (selectedHour > 12) ? selectedHour - 12 : selectedHour;
                                ti = String.format("%02d:%02d %s", selectedHour, selectedMinute, amPm);

                                time.setText(ti);
                            }
                        },
                        hour, minute, false // 24-hour format
                );

                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        btn.setOnClickListener(v -> {
            scheduled();
        });
    }

     void scheduled()   {
         com.android.volley.RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
         StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
             @Override

             public void onResponse(String response) {

                 if (!response.trim().equals("failed")) {
                     Toast.makeText(Faculty_fix_appointment.this, "Appointment Fixed successfully ", Toast.LENGTH_SHORT).show();
                     startActivity(new Intent(getApplicationContext(), Faculty_view_appointment.class));
                 } else {
                     Toast.makeText(getApplicationContext(), "Failed" + response, Toast.LENGTH_SHORT).show();
                 }
             }
         }, new Response.ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError error) {

                 Toast.makeText(getApplicationContext(), "my Error :" + error, Toast.LENGTH_SHORT).show();
                 Log.i("My Error", "" + error);
             }
         }) {
             @Override
             protected Map<String, String> getParams() {
                 Map<String, String> map = new HashMap<String, String>();
                 String f_id;
                 SharedPreferences sh = getApplicationContext().getSharedPreferences("profileprefer", Context.MODE_PRIVATE);
                 f_id=sh.getString("UID","not_data");
                 map.put("requestType", "ScheduledAppointment");
                 map.put("f_id",f_id );
                 map.put("a_id", a_id);
                 map.put("u_id", uid);
                 map.put("time", ti);
                 map.put("date", da);
                 return map;
             }
         };
         queue.add(request);
     }
}
package com.example.grievancecell.faculty;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.grievancecell.R;
import com.example.grievancecell.common.Utility;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Facutly_sent_reply extends AppCompatActivity {
    EditText type,sen_date,g_des,opin,o_des,sol_date;
    Button send;
    String g_id,uid,ty,des,date,op,ode,s_dat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facutly_sent_reply);
        type=findViewById(R.id.typ);
        g_des=findViewById(R.id.des);
        sen_date=findViewById(R.id.date);
        opin=findViewById(R.id.op);
        o_des=findViewById(R.id.descr);
        sol_date=findViewById(R.id.sdate);
        send=findViewById(R.id.btnsent);
        Intent i = getIntent();
        g_id = i.getExtras().getString("g_id");
        uid = i.getExtras().getString("uid");
        ty = i.getExtras().getString("type");
        des = i.getExtras().getString("des");
        date = i.getExtras().getString("date");
        type.setText(ty);
        sen_date.setText(date);
        g_des.setText(des);

        sol_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                int year = mcurrentDate.get(Calendar.YEAR);
                int month = mcurrentDate.get(Calendar.MONTH);
                int day = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(
                        v.getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {
                                selectedMonth += 1;
                                s_dat = selectedMonth + "/" + selectedDay + "/" + selectedYear;
                                sol_date.setText(s_dat);
                            }
                        },
                        year, month, day
                );

                mDatePicker.setTitle("Select Expiry Date");
                mDatePicker.show();
            }
        });

        send.setOnClickListener(v -> {
            op=opin.getText().toString();
            ode=o_des.getText().toString();
            if (op.isEmpty()) {
                Snackbar.make(opin, "Please Enter Your Opinion", Snackbar.LENGTH_LONG)
                        .setAction("dimiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();
            }else if (ode.isEmpty()) {
                Snackbar.make(o_des, "Please Enter Description", Snackbar.LENGTH_LONG)
                        .setAction("dismiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();

            }else if (s_dat.isEmpty()) {
                Snackbar.make(sol_date, "Please Enter Solution Date", Snackbar.LENGTH_LONG)
                        .setAction("dismiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();

            }else {
                sent_reply();
            }
        });
    }

     void sent_reply()  {
         com.android.volley.RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
         StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
             @Override

             public void onResponse(String response) {

                 if (!response.trim().equals("failed")) {
                     Toast.makeText(Facutly_sent_reply.this, "Reply Sent successfully ", Toast.LENGTH_SHORT).show();
                     startActivity(new Intent(getApplicationContext(), Faculty_view_grievance.class));
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
                 map.put("requestType", "Faculty_sent_reply");
                 map.put("f_id",f_id );
                 map.put("g_id", g_id);
                 map.put("u_id", uid);
                 map.put("opinion", op);
                 map.put("des", ode);
                 map.put("date", s_dat);
                 return map;
             }
         };
         queue.add(request);
     }
}
package com.example.grievancecell.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.grievancecell.Login;
import com.example.grievancecell.R;
import com.example.grievancecell.common.Utility;

import java.util.HashMap;
import java.util.Map;

public class Admin_update_status extends AppCompatActivity {
    EditText type,des,date;
    Spinner status;
    Button update;
    String sts,r_id,ty,de,dat,u_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_status);
        type=findViewById(R.id.op);
        des=findViewById(R.id.descr);
        date=findViewById(R.id.sdate);
        update=findViewById(R.id.btnsent);
        status=findViewById(R.id.pro);
        Intent i = getIntent();
        r_id = i.getExtras().getString("r_id");
        u_id = i.getExtras().getString("uid");
        ty = i.getExtras().getString("type");
        de = i.getExtras().getString("des");
        dat = i.getExtras().getString("date");
        type.setText(ty);
        des.setText(de);
        date.setText(dat);

        String peoir[]={"STATUS","Started","Working On","Completed"};

        ArrayAdapter arrayAdapter1 = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, peoir) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;
                if (position == 0) {
                    Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.paprika);
                    textView.setTypeface(typeface);
                    textView.setTextColor(Color.GRAY);
                    textView.setBackgroundColor(Color.LTGRAY);
                } else {
                    textView.setTextColor(Color.MAGENTA);
                    Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.paprika);
                    textView.setTypeface(typeface);
                }
                return view;
            }
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view;
                Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.paprika);
                textView.setTypeface(typeface);
                return view;
            }
        };
        arrayAdapter1.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        status.setAdapter(arrayAdapter1);

        update.setOnClickListener(v -> {
            sts=status.getSelectedItem().toString();
            update_status();
        });
    }

     void update_status() {
         com.android.volley.RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
         StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
             @Override

             public void onResponse(String response) {

                 if (!response.trim().equals("failed")) {
                     Toast.makeText(Admin_update_status.this, "Status Updated successfully", Toast.LENGTH_SHORT).show();
                     startActivity(new Intent(getApplicationContext(), Admin_view_reply
                             .class));
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
                 map.put("requestType", "AdminUpdateStatus");
                 map.put("rid", r_id);
                 map.put("uid", u_id);
                 map.put("status", sts);
                 return map;
             }
         };
         queue.add(request);
     }
}
package com.example.grievancecell.faculty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.grievancecell.R;
import com.example.grievancecell.common.Base64;
import com.example.grievancecell.common.Utility;
import com.example.grievancecell.student.Edit_profile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Faculty_profile extends AppCompatActivity {
    TextView nam,dep,email,add,phn,gen,desgn;
    ImageView edit,img;
    String id,na,de,pa,em,ad,ph,ge,pic,des;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_profile);
        nam=findViewById(R.id.nam);
        desgn=findViewById(R.id.des);
        dep=findViewById(R.id.dep);
        email=findViewById(R.id.em);
        add=findViewById(R.id.add);
        phn=findViewById(R.id.ph);
        gen=findViewById(R.id.gen);
        edit=findViewById(R.id.edit);
        img=findViewById(R.id.imge);
        edit.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), Fac_edit_profile.class);

            Bundle bundle = new Bundle();
            bundle.putString("id", id);
            bundle.putString("nam", na);
            bundle.putString("dep", de);
            bundle.putString("des", des);
            bundle.putString("email", em);
            bundle.putString("add", ad);
            bundle.putString("phone", ph);
            bundle.putString("pass", pa);
            bundle.putString("gen", ge);
            i.putExtras(bundle);
            startActivity(i);
        });

        myprofile();
    }
    void myprofile() {
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("******", response);
                System.out.println("EEE" + response);
                if (!response.trim().equals("failed")) {
                    System.out.println("LOGINDD"+response);
                    String respArr[]= response.trim().split("#");
                    id=respArr[0];
                    na=respArr[1];
                    ge=respArr[2];
                    de=respArr[3];
                    ad=respArr[4];
                    em=respArr[5];
                    pa=respArr[6];
                    ph=respArr[7];
                    des=respArr[8];
                    pic=respArr[9];

                    nam.setText(na);
                    desgn.setText(" Department: " + de);
                    dep.setText(" Designation: " + des);
                    email.setText(" Email: " + em);
                    add.setText(" Address: " + ad);
                    phn.setText(" Contact: " + ph);
                    gen.setText(" Gender: " + ge);

                    try {
                        byte[] imageAsBytes = Base64.decode(pic.getBytes());

                        img.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "my error: " + error, Toast.LENGTH_LONG).show();
                Log.i("My error", "" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                SharedPreferences sharedPreferences = getSharedPreferences("profileprefer", Context.MODE_PRIVATE);
                String id = sharedPreferences.getString("UID", "");
                Map<String, String> map = new HashMap<String, String>();
                map.put("requestType", "FacultyProfile");
                map.put("id", id);
                return map;
            }
        };
        queue.add(request);
    }
}
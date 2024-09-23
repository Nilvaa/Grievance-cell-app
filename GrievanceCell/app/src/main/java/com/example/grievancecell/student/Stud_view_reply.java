package com.example.grievancecell.student;

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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Stud_view_reply extends AppCompatActivity {
    TextView typ,des,opi,opdes,odate,ostatus;
    ImageView img;
    String rid,rgid,rfid,ruid,g_id,ty,de,op,ode,oda,stat,pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stud_view_reply);
        typ=findViewById(R.id.typ);
        des=findViewById(R.id.des);
        opi=findViewById(R.id.op);
        opdes=findViewById(R.id.odes);
        odate=findViewById(R.id.date);
        ostatus=findViewById(R.id.stat);
        img=findViewById(R.id.imgei);
        Intent i = getIntent();
        g_id = i.getExtras().getString("g_id");
        ty = i.getExtras().getString("type");
        de = i.getExtras().getString("des");
        pic = i.getExtras().getString("pic");

        typ.setText("REGARDING: "+ty);
        des.setText("DESCRIPTION: "+de);

        try {
            byte[] imageAsBytes = Base64.decode(pic.getBytes());

            img.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        } catch (IOException e) {

            e.printStackTrace();
        }
         view_reply();
    }

    void view_reply() {
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("******", response);
                System.out.println("EEE" + response);
                if (!response.trim().equals("failed")) {
                    System.out.println("LOGINDD"+response);
                    String respArr[]= response.trim().split("#");
                    rid=respArr[0];
                    rgid=respArr[1];
                    rfid=respArr[2];
                    ruid=respArr[3];
                    op=respArr[4];
                    de=respArr[5];
                    oda=respArr[6];
                    stat=respArr[7];

                    opi.setText("OPINION: " + op);
                    opdes.setText("DESCRIPTION: " + de);
                    odate.setText("WILL SOLVED BY: " + oda);
                    ostatus.setText("STATUS: " + stat);

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
                map.put("requestType", "StudGrievanceRply");
                map.put("id", id);
                return map;
            }
        };
        queue.add(request);
    }
}
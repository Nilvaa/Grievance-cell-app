package com.example.grievancecell.faculty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.grievancecell.R;
import com.example.grievancecell.adaptor.Appointment_adaptor;
import com.example.grievancecell.adaptor.Faculty_view_grie_adaptor;
import com.example.grievancecell.common.SpeakNowModel;
import com.example.grievancecell.common.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;

public class Faculty_view_appointment extends AppCompatActivity {
    ListView list;
    List<SpeakNowModel> arraylist;
    public Appointment_adaptor adap;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_view_appointment);
        list=findViewById(R.id.appoint);

        arraylist=new ArrayList<SpeakNowModel>();
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                String id=arraylist.get(position).getF_id();
//                String nam=arraylist.get(position).getF_nam();
//                String gen=arraylist.get(position).getF_gen();
//                String dep=arraylist.get(position).getF_dep();
//                String add=arraylist.get(position).getF_add();
//                String phn=arraylist.get(position).getF_phone();
//                String email=arraylist.get(position).getF_email();
//                String des=arraylist.get(position).getF_des();
//                String pic=arraylist.get(position).getF_pic();
//                Intent intent=new Intent(getApplicationContext(), Admin_view_faculty_details.class);
//                Bundle bundle=new Bundle();
//                bundle.putString("id", id);
//                bundle.putString("nam", nam);
//                bundle.putString("gender", gen);
//                bundle.putString("depart", dep);
//                bundle.putString("address", add);
//                bundle.putString("phone", phn);
//                bundle.putString("email", email);
//                bundle.putString("des", des);
//                bundle.putString("pic", pic);
//
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });

        view_appointments();
    }


    public void view_appointments(){
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("******", response);
                System.out.println("EEE" + response);
                if (!response.trim().equals("failed")) {
                    Gson gson = new Gson();
                    String data = response.trim();
                    arraylist = Arrays.asList(gson.fromJson(data, SpeakNowModel[].class));
                    adap = new Appointment_adaptor(Faculty_view_appointment.this, arraylist);
                    list.setAdapter(adap);
                    registerForContextMenu(list);

                } else {
                    Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "my error :" + error, Toast.LENGTH_LONG).show();
                Log.i("My error", "" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                SharedPreferences sharedPreferences = getSharedPreferences("profileprefer", Context.MODE_PRIVATE);
                String uid = sharedPreferences.getString("UID", "");
                Map<String, String> map = new HashMap<String, String>();
                map.put("requestType", "FacAppointments");
                map.put("u_id", uid);
                return map;
            }
        };
        queue.add(request);
    }
}
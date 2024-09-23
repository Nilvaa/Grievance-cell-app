package com.example.grievancecell.student;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;

public class AppointmentsFragment extends Fragment {
    EditText tit,des;
    Spinner typ,sent;
    Button req;
    ImageView rec;
    String ti,de,ty,se;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.fragment_appointments, container, false);
        tit=root.findViewById(R.id.tit);
        des=root.findViewById(R.id.des);
        typ=root.findViewById(R.id.pro);
        sent=root.findViewById(R.id.sent);
        req=root.findViewById(R.id.btnreg);
        rec=root.findViewById(R.id.recent);

        rec.setOnClickListener(v -> {
        Intent i=new Intent(getContext(), Student_view_appointments.class);
        getContext().startActivity(i);
        });

        String peoir[]={"PRIORITY","Urgent","Non-Urgent"};

        ArrayAdapter arrayAdapter1 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, peoir) {
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
        typ.setAdapter(arrayAdapter1);

        String desgn[]={"SENT TO","Head Of Department","Vice Principal","Principal"};

        ArrayAdapter arrayAdapter2 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, desgn) {
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
        arrayAdapter2.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        sent.setAdapter(arrayAdapter2);
        req.setOnClickListener(v -> {
            de = des.getText().toString();
            ty = typ.getSelectedItem().toString();
            se = sent.getSelectedItem().toString();
            ti = tit.getText().toString();
            if (ti.isEmpty()) {
                Snackbar.make(tit, "Please Enter Title", Snackbar.LENGTH_LONG)
                        .setAction("dimiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();
            } else if (de.isEmpty()) {
                Snackbar.make(des, "Please Enter Description", Snackbar.LENGTH_LONG)
                        .setAction("dismiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();

            }  else if (ty.isEmpty()) {
                Snackbar.make(typ, "Please Enter Priority", Snackbar.LENGTH_LONG)
                        .setAction("dismiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();
            }else if (se.isEmpty()) {
                Snackbar.make(sent, "Please Choose Receiver", Snackbar.LENGTH_LONG)
                        .setAction("dismiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();
            } else {
                Appointments();
            }
        });
        return root;
    }

    void Appointments() {
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {

                if (!response.trim().equals("failed")) {
                    Toast.makeText(requireActivity(), "Request Sent successfully", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(getContext(), Login.class));
                } else {
                    Toast.makeText(getContext(), "Failed" + response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), "my Error :" + error, Toast.LENGTH_SHORT).show();
                Log.i("My Error", "" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("profileprefer", Context.MODE_PRIVATE);
                String uid = sharedPreferences.getString("UID", "");
                Map<String, String> map = new HashMap<String, String>();
                map.put("requestType", "StudentREqAppointment");
                map.put("u_id", uid);
                map.put("title", ti);
                map.put("sent", se);
                map.put("type", ty);
                map.put("des", de);
                return map;
            }
        };
        queue.add(request);
    }
}
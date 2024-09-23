package com.example.grievancecell.student;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class ProfileFragment extends Fragment {
    TextView nam,rno,dep,cou,email,add,phn,gen;
    ImageView edit,img;
String id,rn,na,de,co,pa,em,ad,ph,ge,pic;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root=  inflater.inflate(R.layout.fragment_profile, container, false);
        nam=root.findViewById(R.id.nam);
        rno=root.findViewById(R.id.rno);
        dep=root.findViewById(R.id.dep);
        cou=root.findViewById(R.id.cou);
        email=root.findViewById(R.id.em);
        add=root.findViewById(R.id.add);
        phn=root.findViewById(R.id.ph);
        gen=root.findViewById(R.id.gen);
        edit=root.findViewById(R.id.edit);
        img=root.findViewById(R.id.imge);

        edit.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), Edit_profile.class);

            Bundle bundle = new Bundle();
            bundle.putString("id", id);
            bundle.putString("nam", na);
            bundle.putString("rno", rn);
            bundle.putString("dep", de);
            bundle.putString("cou", co);
            bundle.putString("email", em);
            bundle.putString("add", ad);
            bundle.putString("phone", ph);
            bundle.putString("pass", pa);
            bundle.putString("gen", ge);
            i.putExtras(bundle);
            startActivity(i);
        });

        myprofile();
        return root;
    }

     void myprofile() {
         com.android.volley.RequestQueue queue = Volley.newRequestQueue(getContext());

         StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
             @Override
             public void onResponse(String response) {
                 Log.d("******", response);
                 System.out.println("EEE" + response);
                 if (!response.trim().equals("failed")) {
                     System.out.println("LOGINDD"+response);
                     String respArr[]= response.trim().split("#");
                     id=respArr[0];
                     rn=respArr[1];
                     na=respArr[2];
                     ge=respArr[3];
                     de=respArr[4];
                     ad=respArr[5];
                     em=respArr[6];
                     pa=respArr[7];
                     ph=respArr[8];
                     co=respArr[9];
                     pic=respArr[10];

                     nam.setText(na);
                     rno.setText(" Roll Number: " + rn);
                     dep.setText(" Department: " + de);
                     cou.setText(" Course: " + co);
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
                     Toast.makeText(getContext(), "No data found", Toast.LENGTH_LONG).show();
                 }
             }
         }, new Response.ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError error) {
                 Toast.makeText(getContext(), "my error: " + error, Toast.LENGTH_LONG).show();
                 Log.i("My error", "" + error);
             }
         }) {
             @Override
             protected Map<String, String> getParams() {
                 SharedPreferences sharedPreferences = getContext().getSharedPreferences("profileprefer", Context.MODE_PRIVATE);
                 String id = sharedPreferences.getString("UID", "");
                 Map<String, String> map = new HashMap<String, String>();
                 map.put("requestType", "myProfile");
                 map.put("id", id);
                 return map;
             }
         };
         queue.add(request);
     }
}
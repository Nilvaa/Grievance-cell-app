package com.example.grievancecell.admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.example.grievancecell.common.SpeakNowModel;
import com.example.grievancecell.common.Utility;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Admin_view_faculty_details extends AppCompatActivity {
    TextView cnam, depart, addres, email, phone,gender,desig;
    ImageView img;
    String id,nam,gen,dep,phn,add,em,des,pic;
    CardView cv;
    SpeakNowModel select = new SpeakNowModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_faculty_details);
        cnam=findViewById(R.id.nam);
        depart=findViewById(R.id.dep);
        addres=findViewById(R.id.add);
        email=findViewById(R.id.em);
        phone=findViewById(R.id.ph);
        gender=findViewById(R.id.gen);
        desig=findViewById(R.id.des);
        img=findViewById(R.id.imge);
        cv=findViewById(R.id.card);

        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = getIntent();
                id = i.getExtras().getString("id");
                nam = i.getExtras().getString("nam");

                select.setF_id(id);
                select.setF_nam(nam);

                if ("1".equals(select.getL_status())) {
                    showAlreadyAcceptedDialog(select);
                } else {
                    showAcceptDialog(select);
                }


            }
            private void showAcceptDialog(SpeakNowModel user) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(Admin_view_faculty_details.this);
                dialog.setCancelable(false);
                dialog.setTitle("Permission");
//                dialog.setIcon(R.drawable.launch_icon);
                dialog.setMessage("Do you want to Accept? " + user.getF_nam());

                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        accept_fac();
                    }

                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Handle the neutral button click (if needed)
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog alert = dialog.create();
                alert.show();
            }
            private void showAlreadyAcceptedDialog(SpeakNowModel User) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(Admin_view_faculty_details.this);
                dialog.setCancelable(false);
                dialog.setTitle("Already Accepted");
//                dialog.setIcon(R.drawable.launch_icon);
                dialog.setMessage(User.getF_nam() + " has already been accepted.");
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = dialog.create();
                alert.show();
            }

        });
        Intent i = getIntent();
        id = i.getExtras().getString("id");
        nam = i.getExtras().getString("nam");
        gen = i.getExtras().getString("gender");
        dep = i.getExtras().getString("depart");
        add = i.getExtras().getString("address");
        phn = i.getExtras().getString("phone");
        em = i.getExtras().getString("email");
        des = i.getExtras().getString("des");
        pic = i.getExtras().getString("pic");
        cnam.setText(nam);
        depart.setText("DEPARTMENT: " + dep);
        desig.setText("DESIGNATION: " + des);
        email.setText("EMAIL: " + em);
        phone.setText("PHONE: " + phn);
        addres.setText("ADDRESS: " + add);
        gender.setText("GENDER: " + gen);


        try {
            byte[] imageAsBytes = Base64.decode(pic.getBytes());

            img.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
    private void accept_fac() {

        com.android.volley.RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("******", response);
                System.out.println("EEE" + response);
                if (!response.trim().equals("failed")) {
                    Toast.makeText(getApplicationContext(), "Accepted", Toast.LENGTH_LONG).show();
                    updateDocStatus(id, "1");

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

                Map<String, String> map = new HashMap<String, String>();
                map.put("requestType", "AcceptFaculty");
                map.put("id",id);
                return map;
            }
        };
        queue.add(request);
    }
    private void updateDocStatus(String Id, String status) {
        select.setL_status(status);
    }
}
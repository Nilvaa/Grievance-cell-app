package com.example.grievancecell.adaptor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.grievancecell.R;
import com.example.grievancecell.common.Base64;
import com.example.grievancecell.common.SpeakNowModel;
import com.example.grievancecell.faculty.Facutly_sent_reply;

import java.io.IOException;
import java.util.List;

public class Admin_view_grievance_adaptor extends ArrayAdapter<SpeakNowModel> {

    Activity context;
    List<SpeakNowModel> list;

    String g_id,uid,ty,des,date;

    public Admin_view_grievance_adaptor(Activity context, List<SpeakNowModel> list) {
        super(context, R.layout.activity_admin_view_grievance_adaptor, list);
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_admin_view_grievance_adaptor, null, true);

        TextView pn=view.findViewById(R.id.pnam);
        TextView dn=view.findViewById(R.id.dnam);
        TextView se=view.findViewById(R.id.sent);
        TextView i=view.findViewById(R.id.iss);
        TextView de=view.findViewById(R.id.des);
        ImageView im=view.findViewById(R.id.imgei);

        pn.setText("REGARDING: "+list.get(position).getG_typ());
        dn.setText("DESCRIPTION: "+list.get(position).getG_des());
        i.setText("CREATED ON: "+list.get(position).getG_date());
        se.setText("SENT TO: "+list.get(position).getG_sento());
        de.setText("PRIORITY: "+list.get(position).getG_prio());

        String image=list.get(position).getG_pic();
        try {
            byte[] imageAsBytes = Base64.decode(image.getBytes());

            im.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        } catch (IOException e) {

            e.printStackTrace();
        }
        return view;
    }
}
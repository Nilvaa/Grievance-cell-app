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
import com.example.grievancecell.faculty.Faculty_fix_appointment;
import com.example.grievancecell.faculty.Facutly_sent_reply;

import java.io.IOException;
import java.util.List;

public class Student_appointment_adaptor extends ArrayAdapter<SpeakNowModel> {

    Activity context;
    List<SpeakNowModel> list;



    public Student_appointment_adaptor(Activity context, List<SpeakNowModel> list) {
        super(context, R.layout.activity_student_appointment_adaptor, list);
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_student_appointment_adaptor, null, true);

        TextView pn=view.findViewById(R.id.pnam);
        TextView dn=view.findViewById(R.id.dnam);
        TextView se=view.findViewById(R.id.clas);
        TextView de=view.findViewById(R.id.des);

        pn.setText("REGARDING: "+list.get(position).getA_tit());
        dn.setText("DESCRIPTION: "+list.get(position).getA_des());
        se.setText("DATE: "+list.get(position).getSc_date());
        de.setText("TIME: "+list.get(position).getSc_time());


        return view;
    }
}
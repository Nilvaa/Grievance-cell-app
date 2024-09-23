package com.example.grievancecell.adaptor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.grievancecell.R;
import com.example.grievancecell.common.Base64;
import com.example.grievancecell.common.SpeakNowModel;

import java.io.IOException;
import java.util.List;

public class Student_adaptor extends ArrayAdapter<SpeakNowModel> {

    Activity context;
    List<SpeakNowModel> list;

    public Student_adaptor(Activity context, List<SpeakNowModel> list) {
        super(context, R.layout.activity_student_adaptor, list);
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_student_adaptor, null, true);

        TextView rn=view.findViewById(R.id.rno);
        TextView na=view.findViewById(R.id.nam);
        TextView de=view.findViewById(R.id.add);
        TextView pr=view.findViewById(R.id.dep);
        TextView ph=view.findViewById(R.id.ph);
        TextView em=view.findViewById(R.id.em);
        TextView des=view.findViewById(R.id.des);
        TextView ge=view.findViewById(R.id.gen);
        ImageView im=view.findViewById(R.id.imge);
        na.setText(list.get(position).getS_nam());
        rn.setText("ROLL NUMBER: "+list.get(position).getRno());
        pr.setText("DEPARTMENT: "+list.get(position).getS_dep());
        des.setText("COURSE: "+list.get(position).getS_course());
        ph.setText("PHONE: "+list.get(position).getS_phone());
        em.setText("EMAIL: "+list.get(position).getS_email());
        de.setText("ADDRESS: "+list.get(position).getS_add());
        ge.setText("GENDER: "+list.get(position).getS_gender());

        String image=list.get(position).getS_pic();
        try {
            byte[] imageAsBytes = Base64.decode(image.getBytes());

            im.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        } catch (IOException e) {

            e.printStackTrace();
        }
        return view;
    }
}
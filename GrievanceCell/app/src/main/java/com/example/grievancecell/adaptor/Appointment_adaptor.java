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

public class Appointment_adaptor extends ArrayAdapter<SpeakNowModel> {

    Activity context;
    List<SpeakNowModel> list;

    String a_id,uid,nam;


    public Appointment_adaptor(Activity context, List<SpeakNowModel> list) {
        super(context, R.layout.activity_appointment_adaptor, list);
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_appointment_adaptor, null, true);

        TextView pn=view.findViewById(R.id.pnam);
        TextView dn=view.findViewById(R.id.dnam);
        TextView se=view.findViewById(R.id.clas);
        TextView i=view.findViewById(R.id.iss);
        TextView de=view.findViewById(R.id.des);
        Button btn=view.findViewById(R.id.btnsch);

        btn.setOnClickListener(v -> {
            a_id=list.get(position).getA_id();
            uid=list.get(position).getAuid();
            nam=list.get(position).getS_nam();
            Intent intent=new Intent(getContext(), Faculty_fix_appointment.class);
            Bundle bundle=new Bundle();
            bundle.putString("a_id",a_id);
            bundle.putString("uid",uid);
            bundle.putString("nam",nam);
            intent.putExtras(bundle);
            getContext().startActivity(intent);
        });
        pn.setText("SENT FROM: "+list.get(position).getS_nam());
        dn.setText("CLASS: "+list.get(position).getS_course());
        i.setText("REGARDING: "+list.get(position).getA_tit());
        se.setText("DESCRIPTION: "+list.get(position).getA_des());
        de.setText("PRIORITY: "+list.get(position).getA_typ());


        return view;
    }
}
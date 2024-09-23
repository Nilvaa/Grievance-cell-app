package com.example.grievancecell.adaptor;


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
import com.example.grievancecell.student.Stud_view_reply;
import com.example.grievancecell.student.Student_edit_grievance;

import java.io.IOException;
import java.util.List;

public class Grievance_adaptor extends ArrayAdapter<SpeakNowModel> {

    Activity context;
    List<SpeakNowModel> list;
String g_id,date,pro,sent,ty,des,pic;
    public Grievance_adaptor(Activity context, List<SpeakNowModel> list) {
        super(context, R.layout.activity_grievance_adaptor, list);
        this.list = list;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_grievance_adaptor, null, true);

        TextView pn = view.findViewById(R.id.pnam);
        TextView dn = view.findViewById(R.id.dnam);
        TextView i = view.findViewById(R.id.iss);
        TextView d = view.findViewById(R.id.date);
        TextView de = view.findViewById(R.id.des);
        TextView st = view.findViewById(R.id.stat);
        ImageView im = view.findViewById(R.id.imgei);
        Button btnEdit = view.findViewById(R.id.btnEdit);
        Button btnViewReply = view.findViewById(R.id.btnViewReply);

        SpeakNowModel currentItem = list.get(position);

        pn.setText("REGARDING: " + currentItem.getG_typ());
        dn.setText("DESCRIPTION: " + currentItem.getG_des());
        i.setText("CREATED ON: " + currentItem.getG_date());
        de.setText("PRIORITY: " + currentItem.getG_prio());
        d.setText("SENT TO: " + currentItem.getG_sento());
        st.setText("STATUS: " + currentItem.getG_status());
        String image = currentItem.getG_pic();

        try {
            byte[] imageAsBytes = Base64.decode(image.getBytes());
            im.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (currentItem.getG_status().equals("sent")) {
            btnEdit.setVisibility(View.VISIBLE);
            btnViewReply.setVisibility(View.GONE);

            btnEdit.setOnClickListener(v -> {
                g_id=list.get(position).getG_id();
                date=list.get(position).getG_date();
                ty=list.get(position).getG_typ();
                des=list.get(position).getG_des();
                pro=list.get(position).getG_prio();
                sent=list.get(position).getG_sento();
                pic=list.get(position).getG_pic();
                Intent intent=new Intent(getContext(), Student_edit_grievance.class);
                Bundle bundle=new Bundle();
                bundle.putString("g_id",g_id);
                bundle.putString("type",ty);
                bundle.putString("des",des);
                bundle.putString("pic",pic);
                bundle.putString("date",date);
                bundle.putString("sent",sent);
                bundle.putString("pro",pro);
                intent.putExtras(bundle);
                getContext().startActivity(intent);
            });
        } else if (currentItem.getG_status().equals("replied")) {
            btnEdit.setVisibility(View.GONE);
            btnViewReply.setVisibility(View.VISIBLE);

            btnViewReply.setOnClickListener(v -> {
                g_id=list.get(position).getG_id();
                ty=list.get(position).getG_typ();
                des=list.get(position).getG_des();
                pic=list.get(position).getG_pic();
                Intent intent=new Intent(getContext(), Stud_view_reply.class);
                Bundle bundle=new Bundle();
                bundle.putString("g_id",g_id);
                bundle.putString("type",ty);
                bundle.putString("des",des);
                bundle.putString("pic",pic);
                intent.putExtras(bundle);
                getContext().startActivity(intent);
            });
        } else {
            btnEdit.setVisibility(View.GONE);
            btnViewReply.setVisibility(View.GONE);
        }



        return view;
    }
}
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
import com.example.grievancecell.admin.Admin_update_status;
import com.example.grievancecell.common.Base64;
import com.example.grievancecell.common.SpeakNowModel;
import com.example.grievancecell.faculty.Facutly_sent_reply;

import java.io.IOException;
import java.util.List;

public class Admin_view_reply_adaptor extends ArrayAdapter<SpeakNowModel> {

    Activity context;
    List<SpeakNowModel> list;
    Button edit;
    String r_id,uid,ty,des,date,stat;

    public Admin_view_reply_adaptor(Activity context, List<SpeakNowModel> list) {
        super(context, R.layout.activity_admin_view_reply_adaptor, list);
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_admin_view_reply_adaptor, null, true);

        TextView pn=view.findViewById(R.id.typ);
        TextView dn=view.findViewById(R.id.des);
        TextView i=view.findViewById(R.id.op);
        TextView de=view.findViewById(R.id.odes);
        TextView da=view.findViewById(R.id.date);
        TextView sta=view.findViewById(R.id.stat);
        ImageView im=view.findViewById(R.id.imgei);
        edit=view.findViewById(R.id.btnsch);

        edit.setOnClickListener(v -> {
            r_id=list.get(position).getRe_id();
            uid=list.get(position).getRe_uid();
            ty=list.get(position).getG_typ();
            des=list.get(position).getR_des();
            date=list.get(position).getR_date();
            stat=list.get(position).getR_status();
            Intent intent=new Intent(getContext(), Admin_update_status.class);
            Bundle bundle=new Bundle();
            bundle.putString("r_id",r_id);
            bundle.putString("uid",uid);
            bundle.putString("type",ty);
            bundle.putString("des",des);
            bundle.putString("date",date);
            bundle.putString("status",stat);
            intent.putExtras(bundle);
            getContext().startActivity(intent);
        });

        pn.setText("REGARDING: "+list.get(position).getG_typ());
        dn.setText("DESCRIPTION: "+list.get(position).getG_des());
        i.setText("OPINION: "+list.get(position).getR_opinion());
        de.setText("EXPLANATION: "+list.get(position).getR_des());
        da.setText("SOLVED BY: "+list.get(position).getR_date());
        sta.setText("STATUS: "+list.get(position).getR_status());

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
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

public class Facutly_adaptor extends ArrayAdapter<SpeakNowModel> {

    Activity context;
    List<SpeakNowModel> list;

    public Facutly_adaptor(Activity context, List<SpeakNowModel> list) {
        super(context, R.layout.activity_facutly_adaptor, list);
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_facutly_adaptor, null, true);


        TextView na=view.findViewById(R.id.d_nam);
        TextView de=view.findViewById(R.id.d_em);
        TextView pr=view.findViewById(R.id.d_ph);
        ImageView im=view.findViewById(R.id.imge);
        na.setText(list.get(position).getF_nam());
        de.setText("Department: "+list.get(position).getF_dep());
        pr.setText(list.get(position).getF_email());


        String image=list.get(position).getF_pic();
        try {
            byte[] imageAsBytes = Base64.decode(image.getBytes());

            im.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        } catch (IOException e) {

            e.printStackTrace();
        }
        return view;
    }
}
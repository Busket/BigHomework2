package com.example.bighomework2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class AlterPassword extends Fragment {
    ImageView imageView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.activity_alterpassword, container, false);
        imageView=(ImageView)view.findViewById(R.id.imageView2);
        imageView.setImageResource(R.mipmap.huaji);
        return  view;
    }
}

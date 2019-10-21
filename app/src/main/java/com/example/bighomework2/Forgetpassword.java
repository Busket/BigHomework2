package com.example.bighomework2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class Forgetpassword extends Fragment
{
    ImageView imageView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.activity_forgetpassword, container, false);

        imageView=(ImageView)view.findViewById(R.id.imageView2);
        imageView.setImageResource(R.mipmap.huaji);
        return  view;
    }
}

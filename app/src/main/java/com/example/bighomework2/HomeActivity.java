package com.example.bighomework2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view= inflater.inflate(R.layout.acrivity_home, container, false);
        //对View中控件的操作方法
        Button button1=(Button)view.findViewById(R.id.helpbutton);
        Button button2=(Button)view.findViewById(R.id.appealbutton);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"你单击了帮忙按钮",Toast.LENGTH_SHORT).show();
                Intent intent1=new Intent(getContext(),HelpList.class);
                startActivity(intent1);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"你单击了求助按钮",Toast.LENGTH_SHORT).show();
                Intent intent2=new Intent(getContext(),EditInfomation.class);
                startActivity(intent2);
            }
        });
        return view;
    }

}

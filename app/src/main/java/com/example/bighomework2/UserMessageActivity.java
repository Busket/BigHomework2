package com.example.bighomework2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;

public class UserMessageActivity extends Fragment {
    Button button;//强制下线按钮
    TextView name;
    TextView acount_Number;
    //Credit_progressBar
    ProgressBar credibility;
    User user;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_user_message, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){
        //接收User
        user=(User)getArguments().getSerializable("user");
        //声明组件
        button=(Button)view.findViewById(R.id.logout);
        name=(TextView) view.findViewById(R.id.username);
        acount_Number=(TextView) view.findViewById(R.id.useracnum);
        credibility=(ProgressBar)view.findViewById(R.id.Credit_progressBar);
        //Log.i("Credibility", String.valueOf(user.Credibility));

        name.setText(user.Name);
        acount_Number.setText(user.Acount_Number);
        credibility.setProgress(user.Credibility);

//        Person person=(Person)getIntent().getSerializableExtra("person1");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("recover","发送强制下线广播！");
                Intent intent=new Intent("com.example.BigHomework2.FORCE_OFFLINE");
                getActivity().sendBroadcast(intent);

                //将SharePreferences中的数据清除
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("logindata", MODE_PRIVATE).edit();
                editor.clear();//删除文件中的数据，注意，也要执行apply或commit方法
//                          Log.d("login","清除了文件中的账号信息");
            }
        });
    }


}

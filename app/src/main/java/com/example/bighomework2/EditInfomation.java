package com.example.bighomework2;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditInfomation extends AppCompatActivity {

    Button button_commit;//提交按钮
    EditText edittitle,edittime,editaddress,editpunmber,editevent,editaging,editreward,editcontact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_infomation);

        button_commit=findViewById(R.id.button_commit1);
        edittitle=findViewById(R.id.editTitle);
        edittime=findViewById(R.id.editTime);
        editaddress=findViewById(R.id.editAddress);
        editpunmber=findViewById(R.id.editPnumber);
        editevent=findViewById(R.id.editEvent);
        editaging=findViewById(R.id.editAging);
        editreward=findViewById(R.id.editReward);
        editcontact=findViewById(R.id.editContact);
        /*server=new UDPServer();*/

        button_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref=getSharedPreferences("logindata", Context.MODE_PRIVATE);//检测用户是否登录，将所有用户数据导出
                String state=pref.getString("state","");
                if(state.equals("logined")) {
                    String account_Number = pref.getString("account_Number", "");//先获得用户的账号
                    //给User赋值
                    User user = new User();
                    user.Acount_Number = account_Number;

                    String loginAddress="http://192.168.43.142:4399/Login";//在这附上相应的类链接
                    //获得输入的内容
                    String postTitle = edittitle.getText().toString();
                    String postTime = edittime.getText().toString();
                    String postAddress = editaddress.getText().toString();
                    String postPunmber = editpunmber.getText().toString();
                    String postEvent = editevent.getText().toString();
                    String postAging = editaging.getText().toString();
                    String postReward = editreward.getText().toString();
                    String postContact = editcontact.getText().toString();

                    loginWithOkHttp(loginAddress,loginAccount,loginPassword);
                    Log.i("Send","数据已发送！");
                }
                else{
                    Toast.makeText(getApplicationContext(),"您还未登录！请您前往“我的”登录！",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

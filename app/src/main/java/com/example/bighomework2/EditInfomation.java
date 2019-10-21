package com.example.bighomework2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditInfomation extends AppCompatActivity {


//    Button button_commit;
//    EditText edittitle,edittime,editaddress,editpunmber,editevent,editaging,editreward,editcontact;
//    UDPClient client;
//    UDPServer server;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_infomation);


//        button_commit=findViewById(R.id.button_commit1);
//        edittitle=findViewById(R.id.editTitle);
//        edittime=findViewById(R.id.editTime);
//        editaddress=findViewById(R.id.editAddress);
//        editpunmber=findViewById(R.id.editPnumber);
//        editevent=findViewById(R.id.editEvent);
//        editaging=findViewById(R.id.editAging);
//        editreward=findViewById(R.id.editReward);
//        editcontact=findViewById(R.id.editContact);
//        /*server=new UDPServer();*/
//
//        button_commit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                client=new UDPClient(edittitle.getText().toString(),edittime.getText().toString(),editaddress.getText().toString(),editpunmber.getText().toString(),
//                        editevent.getText().toString(),editaging.getText().toString(),editreward.getText().toString(),editcontact.getText().toString());
//                /*server=new UDPServer();*/
//                client.SendData();
//                Toast.makeText(EditInfomation.this,"已发送",Toast.LENGTH_SHORT).show();
//                /* System.out.println("客户端已成功发送消息");*/
//                /*server.getAndsendData();*/
//                /*client.getData();*/
//
//            }
//        });
    }
}

package com.example.bighomework2;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

public class RegisterActivity extends Fragment {
    EditText editText;//账号
    EditText editText2;//密码
    EditText editText3;//确认密码
    Button registerbutton;
    ImageView imageView;//头像
    String ACnumber;//账号，用于检查

    Register register;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.activity_register, container, false);
        initView(view);
        return  view;
    }


    private void initView(View view){
        getFragmentManager().beginTransaction().hide(this);
        editText=(EditText)view.findViewById(R.id.editText);//账号
        editText2=(EditText)view.findViewById(R.id.editText2);//密码
        editText3=(EditText)view.findViewById(R.id.editText3);//确认密码
        registerbutton=(Button)view.findViewById(R.id.registerbutton);//注册按钮
        imageView=(ImageView)view.findViewById(R.id.imageView);
        imageView.setImageResource(R.mipmap.huaji);

        ACnumber=makeACnumber();//先产生一个账号
        editText.setText(ACnumber);//将账号展现出去
        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText2.getText().toString()!=null&&editText3.getText().toString()!=null) {
                    if (editText3.getText().toString().equals(editText2.getText().toString())) {//验证两次密码是否相同
                        String registerAddress = "http://192.168.43.142:4399/Register";
                        String registerAccount = editText.getText().toString();
                        String registerPassword = editText2.getText().toString();
                        registerWithOkHttp(registerAddress, registerAccount, registerPassword);
                    }else{
                        Toast.makeText(getContext(), "两次密码不相同！", Toast.LENGTH_SHORT).show();
                        editText2.setText(null);
                        editText3.setText(null);
                    }
                }else{
                    Toast.makeText(getContext(), "密码不得为空！", Toast.LENGTH_SHORT).show();
                }
            }
        });
}
    public String makeACnumber()//随机获取9位数字组成账号
    {
        String randomStr = "";//自动获得9位数字
        for (int i = 0; i < 9; i++) {
            int random = (int) (Math.random() * 9);
            if (randomStr.indexOf(random + "") != -1) {
                i = i - 1;
            } else {
                randomStr += random;
            }
        }
        return randomStr;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    //实现注册
    public void registerWithOkHttp(String address,String account,String password) {
        HttpUtil.registerWithOkHttp(address, account, password, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //在这里对异常情况进行处理
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {

                //得到服务器返回的具体内容
                final String responseData = response.body().string();
                Log.d("responseData", "结果是："+responseData);
                try {
                    register=new Register();
                    JSONObject object=new JSONObject(responseData);

                    String state=object.getString("state");
                    register.state=state;
                    String registerAccount = object.getString("registerAccount");
                    register.registerAccount=registerAccount;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (register.state.equals("true")){
                            Toast.makeText(getContext(), "注册成功", Toast.LENGTH_SHORT).show();

                            //获取fragment的实例
                            LoginActivity loginActivity=new LoginActivity();
                            //测试：将User传递过去
                            Bundle args = new Bundle();
                            args.putSerializable("loginer",register);//ARG_ARTICLE
                            loginActivity.setArguments(args);

                            //将fregment转移到 登录界面
                            //获取Fragment的管理器
                            FragmentManager fragmentManager=getFragmentManager();
                            //开启fragment的事物,在这个对象里进行fragment的增删替换等操作。
                            FragmentTransaction ft=fragmentManager.beginTransaction();
                            ft.hide(new RegisterActivity());
                            //跳转到fragment，第一个参数为所要替换的位置id，第二个参数是替换后的fragment
                            ft.replace(R.id.mine_layout,loginActivity);
                            //提交事物
                            ft.commit();

                        } else {
                            Toast.makeText(getContext(), "注册失败", Toast.LENGTH_SHORT).show();
                            //账号重复的话重新更新账号
                            ACnumber=makeACnumber();//先产生一个账号
                            editText.setText(ACnumber);//将账号展现出去
                            Toast.makeText(getContext(), "账号重置成功！", Toast.LENGTH_SHORT).show();
                            editText2.setText(null);
                            editText3.setText(null);
                        }
                    }
                });
//git test
//                final String responseData = response.body().string();
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                });
            }
        });
    }
//    private TextWatcher editclick = new TextWatcher() {//文本框的一些输入判断
//
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//
//        }
//
//        //一般我们都是在这个里面进行我们文本框的输入的判断，上面两个方法用到的很少
//        @Override
//        public void afterTextChanged(Editable s) {
//            String  password= editText2.getText().toString();
//            String confirmp=editText3.getText().toString();
//            Pattern p1 = Pattern.compile("[0-9a-zA-Z]*");//设置0-9
//            Matcher m1 = p1.matcher(password);
//            if (m1.matches()) {
//
//            } else {
//                editText2. setError ( "密码请设置为仅包含0-9，小写，大写字母" ) ;
//            }
//            Pattern p2 = Pattern.compile(password);
//            Matcher m2 = p2.matcher(confirmp);
//            if (m2.matches()) {
//
//            } else {
//                editText3. setError ( "密码不正确！" ) ;
//            }
//        }
//    };
}
class Register implements Serializable{
    String state;
    String registerAccount;
}
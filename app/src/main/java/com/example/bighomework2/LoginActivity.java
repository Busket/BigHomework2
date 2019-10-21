package com.example.bighomework2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

public class LoginActivity extends Fragment {
    private Button button;//登录
    private Button button2;//注册
    private Button button3;//忘记密码
    private ImageView imageView;
    private EditText loginAccount_etext;
    private EditText loginPassword_etext;
    Register register;
//    private static final String urlPath = "http://192.168.43.142:4399/Server/JsonServlet";
    //private static final String urlPath = "http://192.168.43.142:4399/Login";
    private static final String TAG = "LoginActivity";
//    private List<User> users;
    static User user;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.activity_login, container, false);
        initView(view);
        return  view;
    }


    private void initView(View view){
        String ac=null;
        try{
            //接收User
            register=(Register)getArguments().getSerializable("loginer");
            ac=register.registerAccount;
        }catch (Exception e){
            e.printStackTrace();
        }

        button=(Button)view.findViewById(R.id.Lbutton);
        button2=(Button)view.findViewById(R.id.Rbutton);
        button3=(Button)view.findViewById(R.id.FPbutton);

        imageView=(ImageView)view.findViewById(R.id.imageView);
        imageView.setImageResource(R.mipmap.huaji);
        loginAccount_etext=(EditText)view.findViewById(R.id.loginAccount_etext);
        loginPassword_etext=(EditText)view.findViewById(R.id.loginPassword_etext);

        if(ac!=null){
            loginAccount_etext.setText(ac);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"单击了登录！",Toast.LENGTH_LONG).show();
                String loginAddress="http://192.168.43.142:4399/Login";//在这附上相应的类链接
                String loginAccount = loginAccount_etext.getText().toString();
                String loginPassword = loginPassword_etext.getText().toString();
                loginWithOkHttp(loginAddress,loginAccount,loginPassword);
                Log.i("Send","数据已发送！");

//                try {
//                    // 得到Json解析成功之后数据
//                    users = JsonParse.getListPerson(urlPath);
//                    List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
//                    for (int i = 0; i < users.size(); i++) {
//                        HashMap<String, Object> map = new HashMap<String, Object>();
//                        map.put("Acount_Number", users.get(i).getAcount_Number());
//                        map.put("Password", users.get(i).getPassword());
//                        map.put("Name", users.get(i).getName());
//                        map.put("Credibility", users.get(i).getCredibility());
//                        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
//                        builder.setTitle("Warming");
//                        builder.setMessage( users.get(i).getAcount_Number()+ users.get(i).getPassword()+users.get(i).getName()+users.get(i).getCredibility());
//                        builder.setCancelable(false);
//                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        });
//                        builder.show();
//                        data.add(map);
//                    }
//                     } catch (Exception e) {
//                    Toast.makeText(getContext(), "解析失败", Toast.LENGTH_SHORT);//在手机上显示提示Toast，2秒
//                    Log.i(TAG, e.toString());//DDMS中显示提示
//                }

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"单击了注册！",Toast.LENGTH_LONG).show();
//        //获取fragment的实例
                RegisterActivity registerActivity=new RegisterActivity();
//        //获取Fragment的管理器
                FragmentManager fragmentManager=getFragmentManager();
//        //开启fragment的事物,在这个对象里进行fragment的增删替换等操作。
                FragmentTransaction ft=fragmentManager.beginTransaction();
                ft.hide(new LoginActivity());
//        //跳转到fragment，第一个参数为所要替换的位置id，第二个参数是替换后的fragment
                ft.replace(R.id.mine_layout,registerActivity);
//        //提交事物
                ft.commit();
            }
        });

        button3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //获取fragment的实例
               Forgetpassword forgetpassword=new Forgetpassword();
//        //获取Fragment的管理器
                FragmentManager fragmentManager=getFragmentManager();
//        //开启fragment的事物,在这个对象里进行fragment的增删替换等操作。
                FragmentTransaction ft=fragmentManager.beginTransaction();
                ft.hide(new LoginActivity());
//        //跳转到fragment，第一个参数为所要替换的位置id，第二个参数是替换后的fragment
                ft.replace(R.id.mine_layout,forgetpassword);
//        //提交事物
                ft.commit();
            }
        });
    }
    //实现登录
    public void loginWithOkHttp(final String address, String account, String password){
        HttpUtil.loginWithOkHttp(address,account,password, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //在这里对异常情况进行处理
                //Toast.makeText(getContext(),"似乎出现了点小问题，请在试一次！",Toast.LENGTH_LONG).show();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                final String responseData = response.body().string();
                Log.d("responseData", "结果是："+responseData);
                try {
                    user=new User();
                    JSONObject object=new JSONObject(responseData);

                    String State=object.getString("state");
                    user.State=State;
                    String Acount_Number = object.getString("acount_Number");
                    user.Acount_Number=Acount_Number;
                    String Password = object.getString("password");
                    user.Password=Password;
                    String Name = object.getString("name");
                    user.Name=Name;
                    //String Avatar = jsonObject.getString("Avatar");
                    int Credibility = object.getInt("credibility");
                    user.Credibility=Credibility;

                    //日志
                    Log.d("State", "结果是："+State);
                    Log.d("Acount_Number", "结果是："+Acount_Number);
                    Log.d("Password", "结果是："+Password);
                    Log.d("Name", "结果是："+Name);
                    Log.d("Credibility", "结果是："+Credibility);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                try{
//                // 得到Json解析成功之后数据
//               user = JsonParse.getListUser(address);//这一句包含了接收，解析，并且转化成user
////                List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
////                for (int i = 0; i < users.size(); i++) {
////                    HashMap<String, Object> map = new HashMap<String, Object>();
////                    map.put("Acount_Number", users.get(i).getAcount_Number());
////                    map.put("Password", users.get(i).getPassword());
////                    map.put("Name", users.get(i).getName());
////                    data.add(map);
////                }
////                SimpleAdapter _Adapter = new SimpleAdapter(MainActivity.this,
////                        data, R.layout.listview_item, new String[] { "name",
////                        "address", "age" }, new int[] { R.id.textView1,
////                        R.id.textView2, R.id.textView3 });
////                mListView.setAdapter(_Adapter);
//            } catch (Exception e) {
//                //Toast.makeText(getContext(), "解析失败", Toast.LENGTH_LONG);//在手机上显示提示Toast，2秒
//                Log.i(TAG, e.toString());//DDMS中显示提示
//
//            }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (user.State.equals("true")){
                            Toast.makeText(getContext(),"登录成功",Toast.LENGTH_SHORT).show();
                            Log.i("receive","登录成功！");

                            //获取可以访问datasf1.xml文件的SharedPreferences对象的Editor对象，
                            //通过该Editor对象可以修改文件中的值
                            SharedPreferences.Editor editor = getActivity().getSharedPreferences("logindata", MODE_PRIVATE).edit();
                            //在Editor中设置键值对，第一个参数为键，第二个为值，
                            // 这些值通过调用editor的apply（）或commit（）方法被写入文件中

                            editor.putString("state", "logined");//存储登录状态信息
                            editor.putString("account_Number", user.Acount_Number);
                            editor.putString("password", user.Password);
                            editor.putString("name", user.Name);
                            editor.putInt("credibility", user.Credibility);

                            Log.d("login","登录的用户信息已写入");

//                          } else {
//                          //删除文件中的数据，注意，也要执行apply或commit方法
//                          editor.clear();//删除文件中的数据，注意，也要执行apply或commit方法
//                          Log.d("login","清除了文件中的账号信息");
//                          }
//                          editor.apply();//把editor的修改更新到文件

//                            Intent intent = new Intent(getContext(), UserMessageActivity.class);
//                            intent.putExtra("user",user);
//                            startActivity(intent);


//                            ArticleConententFragment fragment = new ArticleConententFragment();
//                            Bundle args = new Bundle();
//                            args.putSerializable(ARG_ARTICLE, user);
//
//                            fragment.setArguments(args);

                        //获取fragment的实例
                            UserMessageActivity userMessageActivity=new UserMessageActivity();
                            //测试：将User传递过去
                            Bundle args = new Bundle();
                            args.putSerializable("user", user);//ARG_ARTICLE
                            userMessageActivity.setArguments(args);

                            //获取Fragment的管理器
                            FragmentManager fragmentManager=getFragmentManager();
                            //开启fragment的事物,在这个对象里进行fragment的增删替换等操作。
                            FragmentTransaction ft=fragmentManager.beginTransaction();
                            ft.hide(new LoginActivity());
                            //跳转到fragment，第一个参数为所要替换的位置id，第二个参数是替换后的fragment
                            ft.replace(R.id.mine_layout,userMessageActivity);
                            //提交事物
                            ft.commit();
                        }else{
                            Toast.makeText(getContext(),"登录失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}

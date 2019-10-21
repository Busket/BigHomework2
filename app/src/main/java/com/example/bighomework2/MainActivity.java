package com.example.bighomework2;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private TextView mTextMessage;

    private IntentFilter intentFilter;//网络广播接收器
    private NetworkChangeReceiver networkChangeReceiver;

    private ForceOfflineReceiver receiver;//强制下线广播接收器

    List<Fragment> fragments;
    ViewPager vp;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                    case R.id.navigation_home:
                        vp.setCurrentItem(0);
                        return true;
                    case R.id.navigation_discover:
                        vp.setCurrentItem(1);
                        return true;
                    case R.id.navigation_mine:
                        vp.setCurrentItem(2);
                        return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCollector.addActivity(this);

        //DEBUG 191005 WORK! 解决了包不能发送的问题
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //DEBUG 191008 TEST
        //得到wifi管理器
//        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//        WifiManager.MulticastLock lock= wifiManager.createMulticastLock("test wifi");

//        WifiManager wmanager =(WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//        //1.判断wifi是否打开
//        if(!wifiManager.isWifiEnabled()){
//            //打开wifi
//            wifiManager.setWifiEnabled(true);
//            Toast.makeText(getApplicationContext(),"打开了wifi",Toast.LENGTH_LONG).show();
//        }
//        lock.acquire();


        mTextMessage = (TextView) findViewById(R.id.message);
        //下面三个按钮设置监听器
        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //navigation.setSelectedItemId(navigation.getMenu().getItem(2).getItemId());

        intentFilter =new IntentFilter();//网络 广播接收器，用于检测网络
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver=new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver,intentFilter);

        mTextMessage = (TextView) findViewById(R.id.message);

        fragments=new ArrayList<Fragment>();//各页面显示的内容
        fragments.add(new HomeActivity());
        fragments.add(new DiscoverActivity());
        fragments.add(new MineActivity());
        FragAdapter adapter = new FragAdapter(getSupportFragmentManager(), fragments);

        //设定适配器
        vp= (ViewPager)findViewById(R.id.viewpager_launch);
        vp.setAdapter(adapter);

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {//添加了一个viewPager的监听器，完成了navigation的改变
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //注意这个方法滑动时会调用多次，下面是参数解释：
                //position当前所处页面索引,滑动调用的最后一次绝对是滑动停止所在页面
                //positionOffset:表示从位置的页面偏移的[0,1]的值。
                //positionOffsetPixels:以像素为单位的值，表示与位置的偏移
            }

            @Override
            public void onPageSelected(int position) {
                //该方法只在滑动停止时调用，position滑动停止所在页面位置
                //当滑动到某一位置，导航栏对应位置被按下
                navigation.getMenu().getItem(position).setChecked(true);
                //这里使用navigation.setSelectedItemId(position);无效，
                //setSelectedItemId(position)的官网原句：Set the selected
                // menu item ID. This behaves the same as tapping on an item
                //未找到原因
            }

            @Override
            public void onPageScrollStateChanged(int state) {
//              这个方法在滑动是调用三次，分别对应下面三种状态
//              这个方法对于发现用户何时开始拖动，
//              何时寻呼机自动调整到当前页面，或何时完全停止/空闲非常有用。
//              state表示新的滑动状态，有三个值：
//              SCROLL_STATE_IDLE：开始滑动（空闲状态->滑动），实际值为0
//              SCROLL_STATE_DRAGGING：正在被拖动，实际值为1
//              SCROLL_STATE_SETTLING：拖动结束,实际值为2
            }
        });

    }

    @Override
    protected void  onResume(){
        super.onResume();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("com.example.BigHomework2.FORCE_OFFLINE");
        receiver=new ForceOfflineReceiver();
        registerReceiver(receiver,intentFilter);
    }
    @Override
    protected void onDestroy()//当程序进入销毁阶段时
    {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);//移除网络监听器
        ActivityCollector.removeActivity(this);//移除强制下线的Activity
    }
    class NetworkChangeReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)//网络状态的广播接收器器
        {
            ConnectivityManager connectivityManager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
            if(networkInfo!=null && networkInfo.isAvailable())
            {
                //Toast.makeText(context,"客官，小弟好像找不到店了！",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(context,"客官，没有网络，小弟找不着北！",Toast.LENGTH_SHORT).show();
            }
        }
    }

}

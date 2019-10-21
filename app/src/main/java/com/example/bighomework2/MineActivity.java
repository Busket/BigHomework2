package com.example.bighomework2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MineActivity extends Fragment implements View.OnClickListener{

    private ViewPager mViewPager;
    private List<Fragment> fragmentList;
    private VpAdapter mAdapter;

    private LoginActivity loginFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_mine, container, false);//这里是否需要吧那个改成mine的作为默认
        initView(view);

//        //获取fragment的实例
//        Fragment fragment=new Fragment();
//        //获取Fragment的管理器
//        FragmentManager fragmentManager=getFragmentManager();
//        //开启fragment的事物,在这个对象里进行fragment的增删替换等操作。
//        FragmentTransaction ft=fragmentManager.beginTransaction();
//        //跳转到fragment，第一个参数为所要替换的位置id，第二个参数是替换后的fragment
//        ft.replace(R.layout.activity_login,fragment);
//        //提交事物
//        ft.commit();
        return view;
    }

    private void initView(View view) {

        //mViewPager.addOnPageChangeListener(this);

        fragmentList = new ArrayList<>();
        fragmentList.add(new LoginActivity());
        fragmentList.add(new RegisterActivity());
        fragmentList.add(new UserMessageActivity());
//        mAdapter = new VpAdapter(getChildFragmentManager());

//        mViewPager = view.findViewById(R.id.viewpager_mine);
//        mViewPager.setAdapter(mAdapter);
        //mViewPager.setSlide(false);

        SharedPreferences pref=getActivity().getSharedPreferences("logindata",Context.MODE_PRIVATE);
        String state=pref.getString("state","");
        if(state.equals("logined")){
            String account_Number=pref.getString("account_Number","");//将所有用户数据导出
            String password=pref.getString("password","");
            String name=pref.getString("name","");
            int credibility=pref.getInt("credibility",0);
            //给User赋值
            User user=new User();
            user.State="true";
            user.Acount_Number=account_Number;
            user.Password=password;
            user.Name=name;
            user.Credibility=credibility;

            UserMessageActivity userMessageActivity=new UserMessageActivity();
            //测试：将User传递过去
            Bundle args = new Bundle();
            args.putSerializable("user", user);//ARG_ARTICLE
            userMessageActivity.setArguments(args);

            FragmentManager fm =getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.mine_layout,fragmentList.get(2));
            ft.commit();
        }else{
            FragmentManager fm =getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.mine_layout,fragmentList.get(0));
            ft.commit();
        }
    }

//    @Override
//    public void onPageSelected(int position) {
//
//    }
//
//    @Override
//    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//    }
//
//    @Override
//    public void onPageScrollStateChanged(int state) {
//
//    }




        @Override
    public void onClick(View v) {

//        switch (v.getId()) {
//            case R.id.Rbutton:
//                Toast.makeText(getContext(),"单击了注册按钮！",Toast.LENGTH_LONG).show();
////                mViewPager.setCurrentItem(0);
//              FragmentManager fm =getFragmentManager();
//              FragmentTransaction ft = fm.beginTransaction();
//              RegisterActivity re = new RegisterActivity();
//              ft.replace(R.id.Rbutton, re);
//                break;
//
//            case R.id.tv_yijiedianhua:
//
//                mViewPager.setCurrentItem(1);
//                break;
//
//            case R.id.tv_weijiedianhua:
//
//                mViewPager.setCurrentItem(2);
//                break;
//        }
    }

//    public class ViewPagerSlide extends ViewPager {
//        //是否可以进行滑动
//        private boolean isSlide = false;
//
//        public void setSlide(boolean slide) {
//            isSlide = slide;
//        }
//        public ViewPagerSlide(Context context) {
//            super(context);
//        }
//
//        public ViewPagerSlide(Context context, AttributeSet attrs) {
//            super(context, attrs);
//        }
//        @Override
//        public boolean onInterceptTouchEvent(MotionEvent ev) {
//            return isSlide;
//        }

    class VpAdapter extends FragmentPagerAdapter {
        VpAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }
        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

}


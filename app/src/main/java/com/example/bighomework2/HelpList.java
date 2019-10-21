package com.example.bighomework2;

import android.os.Handler;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HelpList extends AppCompatActivity  {


    private MyAdapter myAdapter;//我的适配器，应该是用于适配下拉刷新神马的
    private LinearLayoutManager layoutManager;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private Handler handler;
    private List<Integer> listNumber = new ArrayList<>();
    private List<Order> listData = new ArrayList<Order>();

    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_list);
        init();

}

    private void init() {
        myAdapter = new MyAdapter();//设置适配器
        handler = new Handler();//异步消息的处理
        layoutManager = new LinearLayoutManager(this);

        myAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getApplicationContext(),
                        "你单击了： " + position, Toast.LENGTH_SHORT).show();
            }

//            @Override
////            public void onItemLongClick(View view, int position) {
////                Toast.makeText(getApplicationContext(),
////                        "long click: " + position, Toast.LENGTH_SHORT).show();
////            }
        });

        //找到控件们，这两个主要是下拉刷新的
        refreshLayout = findViewById(R.id.swiperefreshlayout);
        recyclerView = findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myAdapter);

        //设置下拉时圆圈的颜色（可以由多种颜色拼成（还没试过））
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light);
        //设置下拉时圆圈的背景颜色（这里设置成白色）
        refreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {//刷新的动作在这！！！！！
                getData("refresh");
            }
        });

        recyclerView.addOnScrollListener(new onLoadMoreListener() {
            protected void onLoading(int countItem, int lastItem) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getData("loadMore");
                    }
                }, 3000);//300是延时时间单位ms
            }
        });

        getData("reset");
    }


    private void getData(final String type) {//在此获得数据
        if ("refresh".equals(type)) {//若是下拉刷新的话，重置后再一个个加
            listNumber.clear();
            count = 0;
            for (int i = 1; i < 20; i++) {
                count += 1;
                listNumber.add(count);

            }
        } else {//不是下拉刷新的话 （初始状态在这设置）
            for (int i = 0; i < 12; i++) {
                count += 1;
                listNumber.add(count);
            }
        }

        myAdapter.notifyDataSetChanged();//notifyDataSetChanged()会记住你划到的位置,重新加载数据的时候不会改变位置,只是改变了数据;而用notifyDataSetInvalidated()时,数据改变的同时,自动滑到顶部第0条的位置.
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
        if ("refresh".equals(type)) {//要么是刷新，要么是加载
            Toast.makeText(getApplicationContext(), "刷新完毕", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "加载完毕", Toast.LENGTH_SHORT).show();
        }
    }

    private  class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private final static int TYPE_CONTENT = 0;//正常内容
        private final static int TYPE_FOOTER = 1;//加载View

        private OnItemClickListener mOnItemClickListener;

        @Override
        public int getItemViewType(int position) {
            if (position == listNumber.size()) {
                return TYPE_FOOTER;
            }
            return TYPE_CONTENT;
        }

        //recyclerview的onCreateViewHolder中：根据viewType来判断recyclerview应该绑定哪个viewHolder。
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_FOOTER) {
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_main_foot, parent, false);
                return new FootViewHolder(view);
            } else {
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_main_item, parent, false);
                MyViewHolder myViewHolder = new MyViewHolder(view);
                return myViewHolder;
            }
        }




        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition();
                        mOnItemClickListener.onItemClick(holder.itemView, pos);
                    }
                });

//                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                    @Override
//                    public boolean onLongClick(View v) {
//                        int pos = holder.getLayoutPosition();
//                        mOnItemClickListener.onItemLongClick(holder.itemView, pos);
//                        return false;
//                    }
//                });
            }

            if (getItemViewType(position) == TYPE_FOOTER) {
            } else {                                                                                 //添加数据
                MyViewHolder viewHolder = (MyViewHolder) holder;
                viewHolder.textView.setText("第" + position + "行");
//                viewHolder.button.setFocusable(false);
//                viewHolder.button.setTag(position);
//                viewHolder.button.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(getApplicationContext(),"第" + position + "行的详细信息！",Toast.LENGTH_LONG).show();
//                    }
//                });
            }
            layoutManager.getChildCount();//返回直接子元素的数量
            layoutManager.getItemCount();//返回列表中的数量
            layoutManager.findLastVisibleItemPosition();//获取最后一个可见view的位置  可能用于后面的下拉
        }



        public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
            this.mOnItemClickListener = mOnItemClickListener;
        }

        @Override
        public int getItemCount() {//获取列表数量
            return listNumber.size() + 1;
        }
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private TextView textView2;
        private TextView textView3;
        private Button button;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textItem);
            //button=(Button)findViewById(R.id.bu);//按钮
    }
    }

    public interface OnItemClickListener
    {
        void onItemClick(View view, int position);

       // void onItemLongClick(View view, int position);
    }


    private class FootViewHolder extends RecyclerView.ViewHolder {
        ContentLoadingProgressBar contentLoadingProgressBar;

        public FootViewHolder(View itemView) {
            super(itemView);
            contentLoadingProgressBar = itemView.findViewById(R.id.pb_progress);
        }
    }

    public abstract class onLoadMoreListener extends RecyclerView.OnScrollListener { //下拉监听器
        private int countItem;//总数量
        private int lastItem;//最后显示的position   137行！
        private boolean isScolled = false;//是否可以滑动
        private RecyclerView.LayoutManager layoutManager;

        /**
         * 加载回调方法
         *
         * @param countItem 总数量
         * @param lastItem  最后显示的position
         */
        protected abstract void onLoading(int countItem, int lastItem);

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
     /* 测试这三个参数的作用
        if (newState==SCROLL_STATE_IDLE){
            Log.d("test","SCROLL_STATE_IDLE,空闲");
        }
        else if (newState==SCROLL_STATE_DRAGGING){
            Log.d("test","SCROLL_STATE_DRAGGING,拖拽");
        }
        else if (newState==SCROLL_STATE_SETTLING){
            Log.d("test","SCROLL_STATE_SETTLING,固定");
        }
        else{
            Log.d("test","其它");
        }*/
            //拖拽或者惯性滑动时isScolled设置为true
            if (newState == RecyclerView.SCROLL_STATE_DRAGGING || newState == RecyclerView.SCROLL_STATE_SETTLING) {
                isScolled = true;
            } else {
                isScolled = false;
            }

        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                layoutManager = recyclerView.getLayoutManager();
                countItem = layoutManager.getItemCount();//这里似乎是list的极限
                //countItem = 20;//这里似乎是list的极限
                lastItem = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
            }
            if (isScolled && countItem != lastItem && lastItem == countItem - 1) {
                onLoading(countItem, lastItem);
            }
        }
    }
}

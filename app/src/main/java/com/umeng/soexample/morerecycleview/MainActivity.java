package com.umeng.soexample.morerecycleview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.umeng.soexample.morerecycleview.adapter.MyAdapter;
import com.umeng.soexample.morerecycleview.bean.User;
import com.umeng.soexample.morerecycleview.presenter.PresenterImpl;
import com.umeng.soexample.morerecycleview.view.IView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IView, XRecyclerView.LoadingListener {
    private XRecyclerView xrv_news;
    private String mUrls = "http://www.xieast.com/api/news/news.php?page=1";
    private int mIndex = 1;
    private ArrayList<User.DataBean> mList = new ArrayList<>();
    private MyAdapter mAdapter;
    private PresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initData();

    }

    private void initData() {
        mAdapter = new MyAdapter(this,mList);
        mAdapter.setOnItemClick(new MyAdapter.ItemClick() {
            @Override
            public void setOnItem(View v, int position) {
                Toast.makeText(MainActivity.this, mList.get(position).getUrl()+"", Toast.LENGTH_SHORT).show();
            }
        });
        presenter = new PresenterImpl(this);
        presenter.start(mUrls,mIndex);
        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
        manager.setOrientation(LinearLayout.VERTICAL);
        xrv_news.setLayoutManager(manager);
        xrv_news.setAdapter(mAdapter);
        xrv_news.setLoadingListener(this);
        xrv_news.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
        xrv_news.getDefaultRefreshHeaderView()
                .setRefreshTimeVisible(true);
    }

    private void initViews() {
        xrv_news = findViewById(R.id.xrv_news);
    }


    @Override
    public void seccess(Object success) {
        User user = (User) success;
        List<User.DataBean> list = user.getData();
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();;
    }

    @Override
    public void error(Object error) {

    }

    @Override
    public void onRefresh() {
        mIndex = 1;
        mList.clear();
        presenter.start(mUrls,1);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                xrv_news.refreshComplete();
            }
        },2000);
    }

    @Override
    public void onLoadMore() {
        mIndex++;

        presenter.start(mUrls,1);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                xrv_news.refreshComplete();
            }
        },2000);
    }
}

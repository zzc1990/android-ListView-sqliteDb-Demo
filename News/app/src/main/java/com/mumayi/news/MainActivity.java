package com.mumayi.news;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mumayi.news.adapter.MyListViewAdapter;
import com.mumayi.news.bean.NewsBean;
import com.mumayi.news.db.NewsDaoUtils;
import com.mumayi.news.utils.GetDataUtils;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private ListView la_lv;
    ArrayList<NewsBean> lists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        ininData();
        listViewItemClick();

    }

    private void listViewItemClick() {
        la_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                NewsBean bean = (NewsBean) parent.getItemAtPosition(position);
                String news_url = bean.news_url;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(news_url));
                startActivity(intent);
            }
        });
    }

    private void initView() {
        la_lv = (ListView) findViewById(R.id.la_lv);
    }

    private void ininData() {


        new Thread(new Runnable() {
            @Override
            public void run() {

                ArrayList<NewsBean> localList = GetDataUtils.getFromLocal(MainActivity.this);
                if (localList != null && localList.size() > 0) {
                    Message msg0 = Message.obtain();
                    msg0.what = 0;
                    handler.sendMessage(msg0);

                } else {
                    //从服务端获取数据 , 获取到数据之后,写入本地数据库
                    ArrayList<NewsBean> serverList = GetDataUtils.getFromServer();
                    //将 serverList 写入数据库中, 注 : 和从服务器取数据一样,都放在子线程
                    NewsDaoUtils newsDaoUtils = new NewsDaoUtils(MainActivity.this);
                    newsDaoUtils.delete(); //存之前,先清空原来的数据
                    newsDaoUtils.insert(serverList);
                    Message msg = Message.obtain();
                    msg.what = 1;
                    handler.sendMessage(msg);

                }

            }
        }).start();


    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);


            switch (msg.what) {
                case 0:
                    ArrayList<NewsBean> localList = (ArrayList<NewsBean>) msg.obj;
                    MyListViewAdapter myListViewAdapter = new MyListViewAdapter(MainActivity.this, localList);
                    la_lv.setAdapter(myListViewAdapter);
                    break;
                case 1:
                    ArrayList<NewsBean> serverList = (ArrayList<NewsBean>) msg.obj;
                    MyListViewAdapter myListViewAdapter1 = new MyListViewAdapter(MainActivity.this, serverList);
                    la_lv.setAdapter(myListViewAdapter1);
                    break;

            }

        }
    };

}

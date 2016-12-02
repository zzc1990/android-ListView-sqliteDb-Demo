package com.mumayi.news.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.mumayi.news.R;
import com.mumayi.news.bean.NewsBean;
import com.mumayi.news.view.MySmartImageView;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by zzc-1990 on 2016/11/6.
 */
public class MyListViewAdapter extends BaseAdapter {


    private final Context context;
    private final ArrayList<NewsBean> list;


    public MyListViewAdapter(Context context, ArrayList<NewsBean> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
//            convertView = View.inflate(context, R.layout.item_news, null);
            convertView = LayoutInflater.from(context).inflate(R.layout.item_news, null);
            holder = new ViewHolder();
            holder.item_img_icon = (MySmartImageView) convertView.findViewById(R.id.item_img_icon);
            holder.item_tv_des = (TextView) convertView.findViewById(R.id.item_tv_des);
            holder.item_tv_title = (TextView) convertView.findViewById(R.id.item_tv_title);
            //绑定tag
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.item_img_icon.setImageUrl(list.get(position).icon_url);
        holder.item_tv_des.setText(list.get(position).des);
        holder.item_tv_title.setText(list.get(position).title);

        return convertView;
    }

    private class ViewHolder {

        public MySmartImageView item_img_icon;
        public TextView item_tv_des, item_tv_title;

    }
}

package com.mumayi.news.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mumayi.news.bean.NewsBean;

import java.util.ArrayList;

/**
 * Created by chenhe on 2016/11/7.
 */
public class NewsDaoUtils {

    private final MySqliteOpenHelper mySqliteOpenHelper;

    //数据库的增删改查方法
    public NewsDaoUtils(Context context) {

        mySqliteOpenHelper = new MySqliteOpenHelper(context);

    }

    //增的方法
    public void insert(ArrayList<NewsBean> list) {
        //通过sqlieopener 对象,获取数据库
        SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();
        //遍历数据添加到数据库中
        for (NewsBean bean : list) {

            ContentValues values = new ContentValues();
            values.put("_id", bean.id);
            values.put("title", bean.title);
            values.put("des", bean.des);
            values.put("time", bean.time);
            values.put("news_url", bean.news_url);
            values.put("icon_url", bean.icon_url);
            values.put("comment", bean.comment);
            values.put("type", bean.type);

            db.insert("news", null, values);//将数据添加到数据库中
        }


        db.close();

    }


    //删的方法
    public void delete() {

        SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();
        db.delete("news", null, null);
        db.close();//凡是创建数据库,用完后都要记得关闭掉

    }


    //查的方法
    public ArrayList<NewsBean> query() {
        ArrayList<NewsBean> list = new ArrayList<>();
        SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from news", null);
        //遍历cursor
        if (cursor != null || cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                NewsBean bean = new NewsBean();
                bean.id = cursor.getInt(cursor.getColumnIndex("_id"));
                bean.comment = cursor.getInt(cursor.getColumnIndex("comment"));
                bean.type = cursor.getInt(cursor.getColumnIndex("type"));
                bean.time = cursor.getString(cursor.getColumnIndex("time"));
                bean.title = cursor.getString(cursor.getColumnIndex("title"));
                bean.des = cursor.getString(cursor.getColumnIndex("des"));
                bean.news_url = cursor.getString(cursor.getColumnIndex("news_url"));
                bean.icon_url = cursor.getString(cursor.getColumnIndex("icon_url"));

                list.add(bean);

            }
        }
        cursor.close();
        db.close();
        return list;
    }


}

package com.mumayi.news.utils;

import android.content.Context;

import com.mumayi.news.bean.NewsBean;
import com.mumayi.news.db.NewsDaoUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by zzc-1990 on 2016/11/6.
 */
public class GetDataUtils {


    public static ArrayList<NewsBean> getFromServer() {

        ArrayList<NewsBean> list = new ArrayList<>();
        //访问服务器获取数据
        try {
            URL url = new URL(Constants.SERVER_URL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(8 * 1000);
            int responseCode = urlConnection.getResponseCode();

            if (responseCode == 200) {

                InputStream inputStream = urlConnection.getInputStream();
                String jsonStr = StreamUtils.streamToString(inputStream);
                //关流,关链接
                inputStream.close();
                urlConnection.disconnect();
                JSONObject jsonObject = new JSONObject(jsonStr);
                JSONArray jsonArray = jsonObject.getJSONArray("news");
                for (int i = 0; i < jsonArray.length(); i++) {
                    NewsBean bean = new NewsBean();
                    JSONObject news_json = (JSONObject) jsonArray.get(i);
                    bean.id = news_json.getInt("id");
                    bean.comment = news_json.getInt("comment");
                    bean.type = news_json.getInt("type");
                    bean.des = news_json.getString("des");
                    bean.title = news_json.getString("title");
                    bean.time = news_json.getString("time");
                    bean.news_url = news_json.getString("news_url");
                    bean.icon_url = news_json.getString("icon_url");

                    list.add(bean);
                }

            }
            return list;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList<NewsBean> getFromLocal(Context context) {

        NewsDaoUtils newsDaoUtils = new NewsDaoUtils(context);
        ArrayList<NewsBean> daoList = newsDaoUtils.query();

        return daoList;
    }
}

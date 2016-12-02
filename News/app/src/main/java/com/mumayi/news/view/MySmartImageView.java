package com.mumayi.news.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MySmartImageView extends ImageView {

    public MySmartImageView(Context context) {
        super(context);
    }

    public MySmartImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MySmartImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    // 主线程创建Handler对象
    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            // 获取子线程发来的数据bitmap,在主线程中更新UI
            Bitmap bitmap = (Bitmap) msg.obj;
            MySmartImageView.this.setImageBitmap(bitmap);// 为当前ImageView对象设置图片资源

        }

        ;
    };

    public void setImageUrl(final String url_str) {

        // 请求网络获取图片资源是一个耗时的操作，需要放到子线程中做
        new Thread(new Runnable() {

            @Override
            public void run() {

                try {

                    // 1.创建一个URL对象
                    URL url = new URL(url_str);
                    // 2.通过URL对象获取一个HttpURLConnection对象
                    HttpURLConnection openConnection = (HttpURLConnection) url.openConnection();
                    // 3.要设置HttpURLConnection对象 的一些参数 : 如:连接的超时时间 ，url的请求方式
                    openConnection.setConnectTimeout(10 * 1000);// 设置连接的超时时间
                    openConnection.setRequestMethod("GET");// 设置url请求方式， 字符串要大写
                    // 4.获取请求的响应码 ； （在获取url返回流信息之前，需要判断响应的状态码是否是200
                    // 如果是，我们去解析流，不是没必要解析）
                    int code = openConnection.getResponseCode();
                    if (code == 200) {// 200 代表成功 206 访问服务器部分资源成功 300 跳转 重定向 400
                        // 资源不存在 500 服务器异常
                        // 5.读取流数据，流数据转换成-----
                        InputStream inputStream = openConnection.getInputStream();

                        // 将一个流转换成图片资源 ； Drawable标示 ， Bitmap 位图

                        // BitmapFactory:Creates Bitmap objects from various
                        // sources, including files, streams, and byte-arrays.
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);// 可以将文件，流，二进制数据转换成Bitmap对象

                        // 创建Message对象,携带获取的数据bitmap
                        Message msg = Message.obtain();
                        msg.obj = bitmap;
                        // 主线程的handler将数据发送到主线程
                        handler.sendMessage(msg);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

}

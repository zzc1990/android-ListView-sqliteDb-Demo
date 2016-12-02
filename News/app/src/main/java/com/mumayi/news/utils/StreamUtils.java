package com.mumayi.news.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StreamUtils {

    public static String streamToString(InputStream inputStream) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, len);
                out.flush();
            }

            String result = out.toString();
            out.close();
            inputStream.close();
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}

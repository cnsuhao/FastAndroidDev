package com.ijustzxh.unit;

import android.content.Context;
import android.util.Log;

import java.io.*;

/**
 * Created by memacjay on 2014/4/15.
 * 文件缓存的工具类
 */
public class FileCacheUtils {

    private static final String TAG = FileCacheUtils.class.getName();
    public static void setUrlCache(Context context,String data, String url) {
        File file = new File(context.getCacheDir() + "/" + getCacheDecodeString(url));
        try {
            //创建缓存数据到磁盘，就是创建文件
            FileUtils.writeTextFile(file,data);
        } catch (IOException e) {
            Log.d(TAG, "write " + file.getAbsolutePath() + " data failed!");
            e.printStackTrace();
        }
    }

    public static String getUrlCache(Context context, String url) {
        if (url == null) {
            return null;
        }
        String reslut = null;
        File file = new File(context.getCacheDir() + "/" + getCacheDecodeString(url));
        if (file.exists() && file.isFile()) {
            try {
                reslut = FileUtils.readTextFile(file);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return reslut ;
    }

    /**
     * 处理特殊字符
     * @param url
     * @return
     */
    public static String getCacheDecodeString(String url) {
        if (url != null) {
            return url.replaceAll("[.:/,%?&=]", "+").replaceAll("[+]+", "+");
        }
        return null;
    }
}

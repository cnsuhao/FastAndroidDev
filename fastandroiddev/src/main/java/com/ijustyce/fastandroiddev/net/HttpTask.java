package com.ijustyce.fastandroiddev.net;

import android.content.Context;

import com.ijustyce.fastandroiddev.baseLib.utils.CommonTool;
import com.ijustyce.fastandroiddev.baseLib.utils.ILog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.Map;

import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by yc on 2015/8/18.  发送网络请求
 */
public class HttpTask {

    private static AsyncHttpClient client = new AsyncHttpClient();

    /**
     * 设置请求时的通用header，value为null则删除
     */
    public static void addHeader(String key, String value) {

        client.removeHeader(key);
        if (value != null && value.replaceAll(" ", "").length() > 0) {
            client.addHeader(key, value);
        }
    }

    public static void cleanHeader() {

        client.removeAllHeaders();
    }

    public static void get(String url, RequestParams params,
                           AsyncHttpResponseHandler responseHandler) {
        ILog.i("===get===", url);
        printHeader();
        client.get(url, params, responseHandler);
    }

    public static void post(String url, RequestParams params,
                            AsyncHttpResponseHandler responseHandler) {
        ILog.i("===post===", url);
        printHeader();
        client.post(url, params, responseHandler);
    }

    public static void printHeader(){

        Map<String, String> map = client.getAllHeader();
        if (map != null) {
            ILog.i("===httpClient===", "start printf header ...");
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                ILog.i(key, value);
            }
        }
    }

    public synchronized  static void postJson(Context context, String url, StringEntity entity
            , AsyncHttpResponseHandler responseHandler) {

        printHeader();
        client.post(context, url, entity, "application/json;charset=utf-8", responseHandler);
    }
}
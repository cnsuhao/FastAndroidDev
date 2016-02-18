package com.ijustyce.fastandroiddev.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.ijustyce.fastandroiddev.R;
import com.ijustyce.fastandroiddev.baseLib.utils.ILog;
import com.ijustyce.fastandroiddev.baseLib.utils.ToastUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.nio.charset.Charset;
import java.util.Map;

import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

/**
 * Created by yc on 2015/8/12.
 */
public class NetWork {

    private static final String TAG = "NetWork";
    private static boolean showToast = true;

    /**
     * whether show ToastUtil , if in thread , please disable it
     *
     * @param value
     */
    public static void showToast(boolean value) {

        showToast = value;
    }

    private NetWork(){

    }

    /**
     * send a get request
     */
    public static synchronized boolean sendGet(Context context, Map<String, Object> map, String url,
                                               AsyncHttpResponseHandler listener) {

        if (!isConnected(context)){
            return false;
        }
        RequestParams params = new RequestParams();
        if (map != null) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (null != value) {
                    params.put(key, value);
                }
            }
            map.clear();
        }
        HttpTask.get(url, params, listener);
        return true;
    }

    /**
     * send a post request
     */
    public static synchronized boolean sendPost(Context context, Map<String, Object> map, String url,
                                                AsyncHttpResponseHandler listener) {

        Log.i("===add parameter===", "add");

        if (!isConnected(context)){
            if (showToast){
                ToastUtil.showTop(R.string.error_network, context);
            }
            return false;
        }
        RequestParams params = new RequestParams();
        if (map != null) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (null != value) {
                    params.put(key, value);
                }
            }
            map.clear();
        }
        HttpTask.post(url, params, listener);
        return true;
    }

    public static synchronized boolean postJson(Context context, String url, String json
            , AsyncHttpResponseHandler listener){

        if (!isConnected(context)){
            if (showToast){
                ToastUtil.showTop(R.string.error_network, context);
            }
            return false;
        }

        ILog.i(url, json == null ? "json is null " : json);

        json = json == null ? "": json;
        StringEntity entity = new StringEntity(json, Charset.forName("utf-8"));
        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=utf-8"));
        HttpTask.postJson(context, url, entity, listener);
        return true;
    }

    /**
     * whether is connected to network .
     *
     * @return true if connect or return false
     */
    public static boolean isConnected(Context context) {

        if (context == null){
            if (showToast) {
                ToastUtil.showTop(R.string.error_network, context);
            }
            return false;
        }

        ConnectivityManager conManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable();
    }

    /**
     * whether is wifi
     * @param context
     * @return true if is wifi or return false
     */

    public static boolean isWifi(Context context){

        if (context == null){
            return false;
        }

        ConnectivityManager connectMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = connectMgr.getActiveNetworkInfo();
        return info!=null && info.getType() == ConnectivityManager.TYPE_WIFI;
    }
}
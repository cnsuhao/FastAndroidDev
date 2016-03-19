package com.ijustyce.fastandroiddev.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.ijustyce.fastandroiddev.R;
import com.ijustyce.fastandroiddev.baseLib.utils.ILog;
import com.ijustyce.fastandroiddev.baseLib.utils.ToastUtil;

import java.util.Map;

/**
 * Created by yc on 2015/8/12.  负责发送网络请求的类
 */
public class NetWork {

    private static HttpParams commonHttpParams;

    private static boolean showToast = true;

    /**
     * whether show ToastUtil , if in thread , please disable it
     *
     * @param value
     */
    public static void showToast(boolean value) {

        showToast = value;
    }

    public static void setHttpParams(HttpParams httpParams){

        commonHttpParams = httpParams;
    }

    /**
     * send a get request
     */
    public static synchronized boolean sendGet(Context context, final HttpParams httpParams,
                                               HttpListener listener) {

        if (!isConnected(context) || httpParams == null){
            return false;
        }

        String url = httpParams.getUrl();
        Map<String, String> map = httpParams.getParams();
        if (commonHttpParams != null && commonHttpParams.getParams() != null){
            map.putAll(commonHttpParams.getParams());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(url).append("?");
        for (String key : map.keySet()){

            stringBuilder.append(key).append("=").append(map.get(key)).append("&");
        }
        url = stringBuilder.toString();

        HttpResponse response = new HttpResponse(url, listener);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response.stringListener, response.errorListener);

        response.setRequest(stringRequest);

        String tag = httpParams.getTag();
        if (tag == null || tag.length() < 1){

            VolleyUtils.addRequest(stringRequest, context);
            ILog.e("===TAG is null===", "http will not be canceled even if activity or fragment stop");
        }else {
            VolleyUtils.addRequest(stringRequest, httpParams.getTag(), context);
        }
        ILog.i("===NetWork url===", httpParams.getUrl());
        ILog.i("===NetWork params===", httpParams.getParams().toString());
        return true;
    }

    /**
     * send a post request
     */
    public static synchronized boolean sendPost(Context context, HttpParams httpParams,
                                                HttpListener listener) {


        if (!isConnected(context)  || httpParams == null){
            return false;
        }
        final Map<String, String> map = httpParams.getParams();
        if (commonHttpParams != null && commonHttpParams.getParams() != null){
            map.putAll(commonHttpParams.getParams());
        }
        HttpResponse response = new HttpResponse(httpParams.getUrl(), listener);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, httpParams.getUrl(),
                response.stringListener, response.errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                return map;
            }
        };

        response.setRequest(stringRequest);

        String tag = httpParams.getTag();
        if (tag == null || tag.length() < 1){

            VolleyUtils.addRequest(stringRequest, context);
            ILog.e("===TAG is null===", "http will not be canceled even if activity or fragment stop");
        }else {
            VolleyUtils.addRequest(stringRequest, httpParams.getTag(), context);
        }

        ILog.i("===NetWork url===", httpParams.getUrl());
        ILog.i("===NetWork params===", httpParams.getParams().toString());
        return true;
    }

    public static synchronized boolean postJson(Context context, String url, HttpParams httpParams
            , HttpListener listener){

        if (!isConnected(context)  || httpParams == null){
            return false;
        }

        HttpResponse response = new HttpResponse(url, listener);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, httpParams.getJson(),
                response.jsonListener, response.errorListener);

        response.setRequest(jsonObjectRequest);

        String tag = httpParams.getTag();
        if (tag == null || tag.length() < 1){

            VolleyUtils.addRequest(jsonObjectRequest, context);
            ILog.e("===TAG is null===", "http will not be canceled even if activity or fragment stop");
        }else {
            VolleyUtils.addRequest(jsonObjectRequest, httpParams.getTag(), context);
        }
        ILog.i("===NetWork url===", httpParams.getUrl());
        ILog.i("===NetWork params===", httpParams.getParams().toString());
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
package com.ijustyce.fastandroiddev.net;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.ijustyce.fastandroiddev.R;
import com.ijustyce.fastandroiddev.baseLib.utils.CommonTool;
import com.ijustyce.fastandroiddev.baseLib.utils.ILog;
import com.ijustyce.fastandroiddev.baseLib.utils.ToastUtil;

import java.util.Map;

/**
 * Created by yc on 2015/8/12.  负责发送网络请求的类
 */
public final class INetWork {

    private static boolean showToast = true;

    /**
     * whether show ToastUtil , if in thread , please disable it
     *
     * @param value
     */
    public static void showToast(boolean value) {

        showToast = value;
    }

    /**
     * 移除某个url对应的缓存，或者清空所有缓存
     * @param url   url，如果为null，则移除所有
     */
    public static void removeCache(String url){

        HttpResponse.removeCache(url);
    }

    /**
     * send a get request
     */
    public static synchronized boolean sendGet(Context context, HttpParams httpParams,
                                               HttpListener listener) {

        if (!CommonTool.isConnected(context) || httpParams == null) {
            if (showToast){
                ToastUtil.showTop(R.string.error_network, context);
            }
            return false;
        }

        String url = httpParams.getUrl();
        if (doCache(httpParams.getCacheTime(), httpParams.getCacheKey(), url, listener)
                && !httpParams.isRefresh()) return true;

        Map<String, String> map = httpParams.getParams();
        final Map<String, String> headers = HttpParams.getHeader();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(url).append("?");
        for (String key : map.keySet()) {

            stringBuilder.append(key).append("=").append(map.get(key)).append("&");
        }
        url = stringBuilder.toString();

        HttpResponse response = new HttpResponse(httpParams.getCacheTime(), httpParams.getCacheKey(), url, listener);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response.stringListener, response.errorListener){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                return headers == null ? super.getHeaders() : headers;
            }
        };

        response.setRequest(stringRequest);

        String tag = httpParams.getTag();
        if (tag == null || tag.length() < 1) {

            VolleyUtils.addRequest(stringRequest, context);
            ILog.e("===TAG is null===", "http will not be canceled even if activity or fragment stop");
        } else {
            VolleyUtils.addRequest(stringRequest, httpParams.getTag(), context);
        }
        ILog.i("===INetWork url===", httpParams.getUrl());
        ILog.i("===INetWork params===", httpParams.getParams().toString());
        return true;
    }

    /**
     * send a post request
     */
    public static synchronized boolean sendPost(Context context, HttpParams httpParams,
                                                HttpListener listener) {


        if (!CommonTool.isConnected(context) || httpParams == null) {
            if (showToast){
                ToastUtil.showTop(R.string.error_network, context);
            }
            return false;
        }

        String url = httpParams.getUrl();
        if (doCache(httpParams.getCacheTime(), httpParams.getCacheKey(), url, listener)
                && !httpParams.isRefresh()) return true;

        final Map<String, String> params = httpParams.getParams();
        final Map<String, String> headers = HttpParams.getHeader();
        HttpResponse response = new HttpResponse(httpParams.getCacheTime(), httpParams.getCacheKey(), url, listener);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response.stringListener, response.errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                return headers == null ? super.getHeaders() : headers;
            }
        };

        response.setRequest(stringRequest);

        String tag = httpParams.getTag();
        if (tag == null || tag.length() < 1) {

            VolleyUtils.addRequest(stringRequest, context);
            ILog.e("===TAG is null===", "http will not be canceled even if activity or fragment stop");
        } else {
            VolleyUtils.addRequest(stringRequest, httpParams.getTag(), context);
        }

        ILog.i("===INetWork url===", httpParams.getUrl());
        ILog.i("===INetWork params===", httpParams.getParams().toString());
        return true;
    }

    public static synchronized boolean postJson(Context context, final HttpParams httpParams
            , HttpListener listener) {

        if (!CommonTool.isConnected(context) || httpParams == null) {
            if (showToast){
                ToastUtil.showTop(R.string.error_network, context);
            }
            return false;
        }

        String url = httpParams.getUrl();
        if (doCache(httpParams.getCacheTime(), httpParams.getCacheKey(), url, listener)
                && !httpParams.isRefresh()) return true;

        final Map<String, String> headers = HttpParams.getHeader();
        HttpResponse response = new HttpResponse(httpParams.getCacheTime(), httpParams.getCacheKey(), url, listener);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, httpParams.getJson(),
                response.jsonListener, response.errorListener){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                return headers == null ? super.getHeaders() : headers;
            }
        };

        response.setRequest(jsonObjectRequest);

        String tag = httpParams.getTag();
        if (tag == null || tag.length() < 1) {

            VolleyUtils.addRequest(jsonObjectRequest, context);
            ILog.e("===TAG is null===", "http will not be canceled even if activity or fragment stop");
        } else {
            VolleyUtils.addRequest(jsonObjectRequest, httpParams.getTag(), context);
        }
        ILog.i("===INetWork url===", httpParams.getUrl());
        ILog.i("===INetWork params===", httpParams.getParams().toString());
        return true;
    }

    /**
     * 上传一组文件
     * files    文件
     * @return  true if success or return false
     */
    public static boolean uploadFile(FormFile[] files, Context context, HttpParams httpParams, ProcessListener listener){

        if (!CommonTool.isConnected(context) || httpParams == null) {
            if (showToast){
                ToastUtil.showTop(R.string.error_network, context);
            }
            return false;
        }

        MultipartRequest request = new MultipartRequest(httpParams.getUrl(),listener, httpParams.getParams(), files);
        String tag = httpParams.getTag();
        if (tag == null || tag.length() < 1) {

            VolleyUtils.addRequest(request, context);
            ILog.e("===TAG is null===", "http will not be canceled even if activity or fragment stop");
        } else {
            VolleyUtils.addRequest(request, httpParams.getTag(), context);
        }
        return true;
    }

    /**
     * 检测cache里是否有这个请求
     */
    private static boolean doCache(int cacheTime, String key, String url , HttpListener listener){

        String tmp = HttpResponse.getCache(cacheTime, key);
        if (tmp == null){
            return false;
        }
        listener.success(tmp, url);
        return true;
    }
}
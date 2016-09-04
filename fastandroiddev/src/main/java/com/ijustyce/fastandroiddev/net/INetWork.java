package com.ijustyce.fastandroiddev.net;

import android.app.Activity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.ijustyce.fastandroiddev.IApplication;
import com.ijustyce.fastandroiddev.R;
import com.ijustyce.fastandroiddev.base.BaseActivity;
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
     *
     * @return  如果 HttpResponse != null 继续执行剩余的代码, 否则不需要执行剩余的代码
     */
    private static HttpResponse buildHttpResponse(HttpParams httpParams, HttpListener listener, Activity activity){

        //  检测网络是否已经连接
        if (!CommonTool.isConnected(IApplication.getInstance())) {
            if (showToast){
                ToastUtil.show(R.string.error_network);
            }
            return null;
        }

        if (httpParams == null){
            ILog.e("===INetWork", "httpParams is null...");
            return null;
        }

        //  如果没有找到缓存而且是BaseActivity,那么通知它,开始网络请求了
        if (activity instanceof BaseActivity){
            httpParams.setTag(((BaseActivity) activity).getTAG());
            if (listener != null && !HttpResponse.getCache(httpParams, listener)) {
                ((BaseActivity) activity).beforeSendRequest(httpParams);
            }
        }
        return new HttpResponse(httpParams, listener);
    }

    private static void requestData(Request<?> request, HttpParams httpParams ){

        String tag = httpParams.getTag();
        if (tag == null || tag.length() < 1) {

            VolleyUtils.addRequest(request, IApplication.getInstance());
            ILog.e("===TAG is null===", "http will not be canceled even if activity or fragment stop");
        } else {
            VolleyUtils.addRequest(request, httpParams.getTag(), IApplication.getInstance());
        }
        ILog.i("===INetWork url===", httpParams.getUrl());
        ILog.i("===INetWork params===", httpParams.getParams().toString());
    }

    /**
     * send a get request
     */
    public static synchronized boolean sendGet(HttpParams httpParams, HttpListener listener, Activity activity) {

        HttpResponse response = buildHttpResponse(httpParams, listener, activity);
        if (response == null) return false;

        Map<String, String> map = httpParams.getParams();
        final Map<String, String> headers = HttpParams.getHeader();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(httpParams.getUrl()).append("?");
        for (String key : map.keySet()) {
            stringBuilder.append(key).append("=").append(map.get(key)).append("&");
        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET, stringBuilder.toString(),
                response.stringListener, response.errorListener){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                return headers == null ? super.getHeaders() : headers;
            }
        };

        response.setRequest(stringRequest);
        requestData(stringRequest, httpParams);
        return true;
    }

    /**
     * send a post request
     */
    public static synchronized boolean sendPost(HttpParams httpParams, HttpListener listener, Activity activity) {

        HttpResponse response = buildHttpResponse(httpParams, listener, activity);
        if (response == null) return false;

        final Map<String, String> params = httpParams.getParams();
        final Map<String, String> headers = HttpParams.getHeader();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, httpParams.getUrl(),
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
        requestData(stringRequest, httpParams);
        return true;
    }

    public static synchronized boolean postJson(final HttpParams httpParams
            , HttpListener listener, Activity activity) {

        HttpResponse response = buildHttpResponse(httpParams, listener, activity);
        if (response == null) return false;

        final Map<String, String> headers = HttpParams.getHeader();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(httpParams.getUrl(), httpParams.getJson(),
                response.jsonListener, response.errorListener){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                return headers == null ? super.getHeaders() : headers;
            }
        };

        response.setRequest(jsonObjectRequest);
        requestData(jsonObjectRequest, httpParams);
        return true;
    }

    public static boolean downloadFile(HttpParams httpParams, String savePath, ITransferListener downloadListener, Activity activity){

        buildHttpResponse(httpParams, null, activity);

        FileDownRequest request = new FileDownRequest(httpParams.getUrl(), downloadListener, savePath);
        String tag = httpParams.getTag();
        if (tag == null || tag.length() < 1) {

            VolleyUtils.addRequest(request, IApplication.getInstance());
            ILog.e("===TAG is null===", "http will not be canceled even if activity or fragment stop");
        } else {
            VolleyUtils.addRequest(request, httpParams.getTag(), IApplication.getInstance());
        }
        return true;
    }

    /**
     * 上传一组文件
     * files    文件
     * @return  true if success or return false
     */
    public static boolean uploadFile(FormFile[] files, HttpParams httpParams, ITransferListener listener, Activity activity){

        buildHttpResponse(httpParams, null, activity);

        MultipartRequest request = new MultipartRequest(httpParams.getUrl(),listener, httpParams.getParams(), files);
        String tag = httpParams.getTag();
        if (tag == null || tag.length() < 1) {

            VolleyUtils.addRequest(request, IApplication.getInstance());
            ILog.e("===TAG is null===", "http will not be canceled even if activity or fragment stop");
        } else {
            VolleyUtils.addRequest(request, httpParams.getTag(), IApplication.getInstance());
        }
        return true;
    }
}
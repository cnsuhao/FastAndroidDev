package com.ijustyce.fastandroiddev.net;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.ijustyce.fastandroiddev.IApplication;
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
    public static synchronized boolean sendGet(HttpParams httpParams, HttpListener listener) {

        if (httpParams == null){
            ILog.e("===INetWork", "httpParams is null...");
            return false;
        }
        String url = httpParams.getUrl();
        if (httpParams.getCacheTime() > 0 &&
                HttpResponse.getCache(httpParams.getCacheTime(),
                        httpParams.getCacheKey(), url, listener)) return true;

        if (!CommonTool.isConnected(IApplication.getInstance())) {
            if (showToast){
                ToastUtil.show(R.string.error_network);
            }
            return false;
        }

        HttpResponse response = new HttpResponse(httpParams.getCacheTime(), httpParams.getCacheKey(), url, listener);
        Map<String, String> map = httpParams.getParams();
        final Map<String, String> headers = HttpParams.getHeader();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(url).append("?");
        for (String key : map.keySet()) {

            stringBuilder.append(key).append("=").append(map.get(key)).append("&");
        }
        url = stringBuilder.toString();

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

            VolleyUtils.addRequest(stringRequest, IApplication.getInstance());
            ILog.e("===TAG is null===", "http will not be canceled even if activity or fragment stop");
        } else {
            VolleyUtils.addRequest(stringRequest, httpParams.getTag(), IApplication.getInstance());
        }
        ILog.i("===INetWork url===", httpParams.getUrl());
        ILog.i("===INetWork params===", httpParams.getParams().toString());
        return true;
    }

    /**
     * send a post request
     */
    public static synchronized boolean sendPost(HttpParams httpParams, HttpListener listener) {

        if (httpParams == null){
            ILog.e("===INetWork", "httpParams is null...");
            return false;
        }
        String url = httpParams.getUrl();
        if (httpParams.getCacheTime() > 0 &&
                HttpResponse.getCache(httpParams.getCacheTime(),
                        httpParams.getCacheKey(), url, listener)) return true;

        if (!CommonTool.isConnected(IApplication.getInstance())) {
            if (showToast){
                ToastUtil.show(R.string.error_network);
            }
            return false;
        }

        final Map<String, String> params = httpParams.getParams();
        final Map<String, String> headers = HttpParams.getHeader();
        HttpResponse response = new HttpResponse(httpParams.getCacheTime(), httpParams.getCacheKey(),
                url, listener);
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

            VolleyUtils.addRequest(stringRequest, IApplication.getInstance());
            ILog.e("===TAG is null===", "http will not be canceled even if activity or fragment stop");
        } else {
            VolleyUtils.addRequest(stringRequest, httpParams.getTag(), IApplication.getInstance());
        }

        ILog.i("===INetWork url===", httpParams.getUrl());
        ILog.i("===INetWork params===", httpParams.getParams().toString());
        return true;
    }

    public static synchronized boolean postJson(final HttpParams httpParams
            , HttpListener listener) {

        if (httpParams == null){
            ILog.e("===INetWork", "httpParams is null...");
            return false;
        }
        String url = httpParams.getUrl();
        if (httpParams.getCacheTime() > 0 &&
                HttpResponse.getCache(httpParams.getCacheTime(),
                        httpParams.getCacheKey(), url, listener)) return true;

        if (!CommonTool.isConnected(IApplication.getInstance())) {
            if (showToast){
                ToastUtil.show(R.string.error_network);
            }
            return false;
        }

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

            VolleyUtils.addRequest(jsonObjectRequest, IApplication.getInstance());
            ILog.e("===TAG is null===", "http will not be canceled even if activity or fragment stop");
        } else {
            VolleyUtils.addRequest(jsonObjectRequest, httpParams.getTag(), IApplication.getInstance());
        }
        ILog.i("===INetWork url===", httpParams.getUrl());
        ILog.i("===INetWork params===", httpParams.getParams().toString());
        return true;
    }

    public static boolean downloadFile(HttpParams httpParams, String savePath, ITransferListener downloadListener){

        if (!CommonTool.isConnected(IApplication.getInstance()) || httpParams == null) {
            if (showToast){
                ToastUtil.show(R.string.error_network);
            }
            return false;
        }
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
    public static boolean uploadFile(FormFile[] files, HttpParams httpParams, ITransferListener listener){

        if (!CommonTool.isConnected(IApplication.getInstance()) || httpParams == null) {
            if (showToast){
                ToastUtil.show(R.string.error_network);
            }
            return false;
        }

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
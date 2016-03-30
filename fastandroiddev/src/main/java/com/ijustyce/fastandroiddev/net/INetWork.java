package com.ijustyce.fastandroiddev.net;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Looper;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.ijustyce.fastandroiddev.R;
import com.ijustyce.fastandroiddev.baseLib.utils.ILog;
import com.ijustyce.fastandroiddev.baseLib.utils.ToastUtil;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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

        if (!isConnected(context) || httpParams == null) {
            return false;
        }

        if (doCache(httpParams.getCacheTime(), httpParams.getCacheKey(), listener)){
            return true;
        }

        String url = httpParams.getUrl();
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


        if (!isConnected(context) || httpParams == null) {
            return false;
        }

        if (doCache(httpParams.getCacheTime(), httpParams.getCacheKey(), listener)){
            return true;
        }

        String url = httpParams.getUrl();
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

        if (!isConnected(context) || httpParams == null) {
            return false;
        }

        if (doCache(httpParams.getCacheTime(), httpParams.getCacheKey(), listener)){
            return true;
        }

        String url = httpParams.getUrl();
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
     * whether is connected to network .
     *
     * @return true if connect or else return false
     */
    public static boolean isConnected(Context context) {

        if (context == null){
            return false;
        }

        ConnectivityManager conManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
        boolean value =  networkInfo != null && networkInfo.isAvailable();
        if (!value && showToast){
            ToastUtil.showTop(R.string.error_network, context);
        }
        return value;
    }

    public static synchronized void upLoadFile(final String uploadUrl, final String path,
                                               final Activity mContext, final HttpListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                try {
                    INetWork.upload(uploadUrl, path, mContext, listener);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Looper.loop();
            }
        }).start();
    }

    /**
     * 阻塞式文件上传
     *
     * @param uploadUrl upload file url
     * @param path      file path to upload
     * @return String of server response
     */
    private static synchronized String upload(String uploadUrl, String path,
                                              Context context, HttpListener listener) throws IOException {

        if (!isConnected(context)) {
            return "false";
        }
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "******";
        URL url = new URL(uploadUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url
                .openConnection();
        httpURLConnection.setChunkedStreamingMode(128 * 1024);// 128K
        // 允许输入输出流
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setUseCaches(false);
        // 使用POST方法
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
        httpURLConnection.setRequestProperty("Charset", "UTF-8");
        httpURLConnection.setRequestProperty("Content-Type",
                "multipart/form-data;boundary=" + boundary);

        DataOutputStream dos = new DataOutputStream(
                httpURLConnection.getOutputStream());
        dos.writeBytes(twoHyphens + boundary + end);
        dos.writeBytes("Content-Disposition: form-data; name=\"uploadfile\"; filename=\""
                + path.substring(path.lastIndexOf("/") + 1) + "\"" + end);
        dos.writeBytes(end);

        FileInputStream fis = new FileInputStream(path);
        byte[] buffer = new byte[8192]; // 8k
        int count = 0;
        // 读取文件
        while ((count = fis.read(buffer)) != -1) {
            dos.write(buffer, 0, count);
        }
        fis.close();
        dos.writeBytes(end);
        dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
        dos.flush();
        InputStream is = httpURLConnection.getInputStream();
        InputStreamReader isr = new InputStreamReader(is, "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String result = br.readLine();
        ILog.i("===INetWork===", result);
        dos.close();
        is.close();
        if (listener != null) {
            listener.success(result, uploadUrl);
        }
        return result;
    }

    /**
     * 检测cache里是否有这个请求
     */
    private static boolean doCache(int cacheTime, String url, HttpListener listener){

        String tmp = HttpResponse.getCache(cacheTime, url);
        if (tmp == null){
            return false;
        }
        listener.success(tmp, url);
        return true;
    }

    /**
     * whether is wifi
     *
     * @param context
     * @return true if is wifi or return false
     */

    public static boolean isWifi(Context context) {

        if (context == null) {
            return false;
        }

        ConnectivityManager connectMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = connectMgr.getActiveNetworkInfo();
        return info != null && info.getType() == ConnectivityManager.TYPE_WIFI;
    }
}
package com.ijustyce.fastandroiddev.net;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ijustyce.fastandroiddev.baseLib.utils.DateUtil;
import com.ijustyce.fastandroiddev.baseLib.utils.IJson;
import com.ijustyce.fastandroiddev.baseLib.utils.ILog;
import com.ijustyce.fastandroiddev.baseLib.utils.StringUtils;
import com.ijustyce.fastandroiddev.contentprovider.CommonData;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by yc on 2015/8/14.  httpResponse
 */
public class HttpResponse {

    private String url;
    private WeakReference<HttpListener> httpListener;
    private int cacheTime;
    private String cacheKey;
    private static Map<String, String> responseData;
    private Request request;
    private Type type;
    private static ArrayList<GlobalHttpListener> listeners = new ArrayList<>();
    private static final String netUser = "fastandroiddev_netUser";

    static {
        responseData = CommonData.getAll(netUser);
    }

    void setRequest(Request request) {

        this.request = request;
    }

    public static void addGlobalListener(GlobalHttpListener listener){
        if (listener == null || listeners == null) return;
        listeners.add(listener);
        ILog.e("===HttpResponse===", "be careful , this may cause memory leaks ...");
    }

    public static void removeGlobalListener(GlobalHttpListener listener){
        if (listener == null || listeners == null) return;
        listeners.remove(listener);
    }

    public static void removeAllGlobalListener(){
        if (listeners != null) {
            listeners.clear();
        }
    }

    public interface GlobalHttpListener{
        void onGetData(Object object);
    }

    HttpResponse(HttpParams params, HttpListener httpListener) {

        if (params == null) {
            throw new IllegalArgumentException("httpParams can not be null...");
        }
        this.url = params.getUrl();
        this.httpListener = new WeakReference<>(httpListener);
        this.cacheTime = params.getCacheTime();
        this.cacheKey = params.getCacheKey();
        this.type = params.getType();
    }

    private void saveCache(String response) {

        if (cacheTime < 0 || StringUtils.isEmpty(response)) return;
        String value = DateUtil.getTimesTamp() + response;
        responseData.put(cacheKey, value);
        CommonData.put(cacheKey, value, netUser);
    }

    /**
     * 移出或清空缓存  如果url为null，则清空所有
     *
     * @param key null则清空所有，否则移出对应的缓存
     */
    public static void removeCache(String key) {

        if (key == null) {
            responseData.clear();
        } else {
            responseData.remove(key);
        }
        CommonData.remove(key, netUser);
    }

    public static String getCache(String cacheKey) {
        return responseData.get(cacheKey);
    }

    static boolean getCache(HttpParams params, HttpListener listener) {

        if (params.getCacheTime() < 0 || listener == null) return false;
        String tmp = getCache(params.getCacheKey());
        if (tmp == null || tmp.length() < 10) {
            return false;
        }
        long putTime = StringUtils.getLong(tmp.substring(0, 10));   //  时间戳只有10位
        long passTime = DateUtil.getTimesTamp() - putTime;
        if (params.getCacheTime() < passTime) {
            listener.success(tmp.substring(10), params.getUrl());
            return false;
        }
        listener.success(tmp.substring(10), params.getUrl());
        return true;
    }

    private void doSuccess(String value) {

        if (requestCanceled()) return;

        if (StringUtils.isEmpty(value)) {
            httpListener.get().fail(-1, value, url);
            return;
        }

        if (type != null){
            Object object = IJson.fromJson(value, type);
            notifyListener(object);
            httpListener.get().success(object, url);
        }else {
            httpListener.get().success(value, url);
        }
        saveCache(value);
    }

    private void notifyListener(Object object){
        if (listeners == null) return;
        for (GlobalHttpListener listener : listeners){
            if (listener != null){
                listener.onGetData(object);
            }
        }
    }

    private boolean requestCanceled(){
        return request == null || request.isCanceled() ||
                httpListener == null || httpListener.get() == null;
    }

    Response.Listener<JSONObject> jsonListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject jsonObject) {

            doSuccess(String.valueOf(jsonObject));
        }
    };

    Response.Listener<String> stringListener = new Response.Listener<String>() {
        @Override
        public synchronized void onResponse(String s) {

            doSuccess(s);
        }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {

        @Override
        public synchronized void onErrorResponse(VolleyError volleyError) {

            if (requestCanceled()) {
                return;
            }
            httpListener.get().fail(-1, "数据请求失败", url);
        }
    };
}
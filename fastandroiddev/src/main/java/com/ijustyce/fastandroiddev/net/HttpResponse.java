package com.ijustyce.fastandroiddev.net;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ijustyce.fastandroiddev.baseLib.utils.DateUtil;
import com.ijustyce.fastandroiddev.baseLib.utils.StringUtils;
import com.ijustyce.fastandroiddev.contentprovider.CommonData;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
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
    private static final String netUser = "fastandroiddev_netUser";

    static {
        responseData = CommonData.getAll(netUser);
    }

    void setRequest(Request request) {

        this.request = request;
    }

    public static GlobalNetData globalNetData;

    public interface GlobalNetData {
        void afterSuccess(String object);
    }

    HttpResponse(int cacheTime, String cacheKey, String url, HttpListener httpListener) {

        this.url = url;
        this.httpListener = new WeakReference<>(httpListener);
        this.cacheTime = cacheTime;
        this.cacheKey = cacheKey;
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

    static boolean getCache(int cacheTime, String cacheKey, String url, HttpListener listener) {

        if (listener == null) return false;
        String tmp = getCache(cacheKey);
        if (tmp == null || tmp.length() < 10) {
            return false;
        }
        long putTime = StringUtils.getLong(tmp.substring(0, 10));   //  时间戳只有10位
        long passTime = DateUtil.getTimesTamp() - putTime;
        if (cacheTime < passTime) {
            listener.success(tmp.substring(10), url);
            return false;
        }
        listener.success(tmp.substring(10), url);
        return true;
    }

    private void doSuccess(String value) {

        if (requestCanceled()) return;

        if (StringUtils.isEmpty(value)) {
            httpListener.get().fail(-1, value, url);
            return;
        }

        httpListener.get().success(value, url);
        if (globalNetData != null) {
            globalNetData.afterSuccess(value);
        }
        saveCache(value);
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
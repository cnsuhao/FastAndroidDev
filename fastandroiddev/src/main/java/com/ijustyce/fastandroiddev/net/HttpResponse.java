package com.ijustyce.fastandroiddev.net;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ijustyce.fastandroiddev.baseLib.utils.DateUtil;
import com.ijustyce.fastandroiddev.baseLib.utils.StringUtils;
import com.ijustyce.fastandroiddev.contentprovider.CommonData;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by yc on 2015/8/14.  httpResponse
 */
public class HttpResponse {

    private String url;
    private HttpListener httpListener;
    private int cacheTime;
    private String cacheKey;
    private static Map<String, String> responseData;
    private Request request;
    private static final String netUser = "fastandroiddev_netUser";

    static {
        responseData = CommonData.getAll(netUser);
    }

    void setRequest(Request request){

        this.request = request;
    }

    public static GlobalNetData globalNetData;

    public interface GlobalNetData{
        public void afterSuccess(String object);
    }

    HttpResponse(int cacheTime, String cacheKey, String url, HttpListener httpListener){

        this.url = url;
        this.httpListener = httpListener;
        this.cacheTime = cacheTime;
        this.cacheKey = cacheKey;
    }

    private void saveCache(String response){

        if (cacheTime > 0 || cacheTime == -1){

            String value = DateUtil.getTimesTamp() + response;
            responseData.put(cacheKey, value);
            CommonData.put(cacheKey, value, netUser);
        }
    }

    /**
     * 移出或清空缓存  如果url为null，则清空所有
     * @param key   null则清空所有，否则移出对应的缓存
     */
    public static void removeCache(String key){

        if (key == null){
            responseData.clear();
        }else {
            responseData.remove(key);
        }
        CommonData.remove(key, netUser);
    }

    static String getCache(int cacheTime, String url){

        String tmp = responseData.get(url);
        if (tmp == null || tmp.length() < 10){
            return null;
        }
        long putTime = StringUtils.getLong(tmp.substring(0, 10));   //  时间戳只有10位
        if (cacheTime != -1 && cacheTime < DateUtil.getTimesTamp() - putTime){
            responseData.remove(url);
            return null;
        }
        return tmp.substring(10);
    }

    private void doSuccess(String value){

        if (StringUtils.isEmpty(value)){
            httpListener.fail(-1, value, url);
            return;
        }
        if (globalNetData != null){
            globalNetData.afterSuccess(value);
        }
        if (httpListener != null){
            httpListener.success(value, url);
        }
        saveCache(value);
    }

    Response.Listener<JSONObject> jsonListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject jsonObject) {

            if (request != null && request.isCanceled()){
                return;
            }
            doSuccess(String.valueOf(jsonObject));
        }
    };

    Response.Listener<String> stringListener = new Response.Listener<String>() {
        @Override
        public synchronized void onResponse(String s) {

            if (request != null && request.isCanceled()){
                return;
            }
            doSuccess(s);
        }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {

        @Override
        public synchronized void onErrorResponse(VolleyError volleyError) {

            if (request != null && request.isCanceled()){
                return;
            }

            if (httpListener != null){
                httpListener.fail(-1, "数据请求失败", url);
            }
        }
    };
}
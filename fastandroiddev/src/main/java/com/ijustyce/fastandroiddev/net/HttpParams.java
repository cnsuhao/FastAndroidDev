package com.ijustyce.fastandroiddev.net;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangchun on 16/3/8.   网络请求的参数组建类
 */
public class HttpParams {

    private Map<String, String> params;
    private String url;
    private String tag;
    private JSONObject json;

    private HttpParams(){

        params = new HashMap<>();
    }

    public static HttpParams create(String tag, String url){

        return new HttpParams().tag(tag).url(url);
    }

    public HttpParams add(String key, Object value){

        if (params == null){
            params = new HashMap<>();
        }

        if (value == null){
            params.remove(key);
        }else {
            params.put(key, String.valueOf(value));
        }
        return this;
    }

    public HttpParams url(String url){

        if (url != null) {
            this.url = url;
        }
        return this;
    }

    public HttpParams tag(String tag){

        if (tag != null) {
            this.tag = tag;
        }
        return this;
    }

    public HttpParams json(JSONObject json){

        this.json = json;
        return this;
    }

    public Map<String, String> getParams(){

        return params;
    }

    public String getUrl(){

        return url;
    }

    public JSONObject getJson(){

        return json;
    }

    public String getTag(){

        return tag;
    }
}
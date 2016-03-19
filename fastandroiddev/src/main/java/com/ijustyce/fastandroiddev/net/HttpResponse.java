package com.ijustyce.fastandroiddev.net;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ijustyce.fastandroiddev.baseLib.utils.ILog;

import org.json.JSONObject;

/**
 * Created by yc on 2015/8/14.  httpResponse
 */
public class HttpResponse {

    private String url;
    private HttpListener httpListener;

    private Request request;

    public void setRequest(Request request){

        this.request = request;
    }

    public HttpResponse(String url, HttpListener httpListener){

        this.url = url;
        this.httpListener = httpListener;
    }

    public Response.Listener<JSONObject> jsonListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject jsonObject) {

            if (request != null && request.isCanceled()){

                ILog.i("===request is canceled===");
                return;
            }

            if (httpListener != null){

                httpListener.success(String.valueOf(jsonObject), url);
            }
        }
    };

    public Response.Listener<String> stringListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String s) {

            if (request != null && request.isCanceled()){

                ILog.i("===request is canceled===");
                return;
            }
            if (httpListener != null){

                if (s == null){
                    httpListener.fail(-2, "response is null", url);
                }else{
                    httpListener.success(s, url);
                }
            }
        }
    };

    public Response.ErrorListener errorListener = new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError volleyError) {

            if (request != null && request.isCanceled()){

                ILog.i("===request is canceled===");
                return;
            }

            if (httpListener != null){
                httpListener.fail(-1, "request failed", url);
            }
        }
    };
}

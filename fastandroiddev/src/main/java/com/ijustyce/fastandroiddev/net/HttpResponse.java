package com.ijustyce.fastandroiddev.net;

import com.ijustyce.fastandroiddev.baseLib.utils.ILog;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

import cz.msebera.android.httpclient.Header;

/**
 * Created by yc on 2015/8/14.
 */
public class HttpResponse extends JsonHttpResponseHandler {

    private static final String SUCCESS = "100";
    private WeakReference<HttpListener> httpInterface;
    private String taskId;

    public HttpResponse(HttpListener httpInterface, String taskId) {
        this.httpInterface = new WeakReference<>(httpInterface);
        this.taskId = taskId;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
        super.onSuccess(statusCode, headers, response);
        ILog.i("===responseString===", response.toString());
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, String responseString) {
        super.onSuccess(statusCode, headers, responseString);
        ILog.i("===responseString===", responseString);
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

        ILog.i(taskId, response.toString());
        if (httpInterface == null || httpInterface.get() == null){
            return;
        }
        parseSuccess(response);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        throwable.printStackTrace();
        if (httpInterface != null && httpInterface.get() != null) {
            httpInterface.get().fail(statusCode, responseString, taskId);
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject json) {
        throwable.printStackTrace();
        ILog.i("===json===", "json is " + json);
        if (httpInterface != null && httpInterface.get() != null) {
            httpInterface.get().fail(statusCode, json == null ? "" : json.toString(), taskId);
        }
    }

    private void parseSuccess(JSONObject json) {

        int code = 0;
        String msg = null;
        try {
            code = json.getInt("code");
            msg = json.getString("message");
            if (code != 0){
                httpInterface.get().fail(code, msg, taskId);
            }else{
                Object result = json.get("result");
                if (result instanceof  JSONObject) {
                    httpInterface.get().success((JSONObject) result, taskId);
                }else{
                    httpInterface.get().success(result, taskId);
                    httpInterface.get().success(null, taskId);
                }
            }
        }catch (JSONException e){
            httpInterface.get().fail(code, msg, taskId);
            e.printStackTrace();
        }
    }
}

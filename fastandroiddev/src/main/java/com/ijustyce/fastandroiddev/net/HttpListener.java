package com.ijustyce.fastandroiddev.net;

import org.json.JSONObject;

/**
 * Created by yc on 2015/8/14.
 */
public interface HttpListener {

    void success(JSONObject data, String taskId);
    void success(Object object, String taskId);
    void fail(int code, String msg, String taskId);
}

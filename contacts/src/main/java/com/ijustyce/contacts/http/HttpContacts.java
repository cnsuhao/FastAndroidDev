package com.ijustyce.contacts.http;

import android.content.Context;

import com.ijustyce.fastandroiddev.net.HttpListener;
import com.ijustyce.fastandroiddev.net.HttpResponse;
import com.ijustyce.fastandroiddev.net.HttpTask;
import com.ijustyce.fastandroiddev.net.NetWork;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by yc on 16-2-12.    获取联系人的接口
 */
public class HttpContacts {

    /**
     *  post json形式向服务端提交数据
     * @param token 登陆的token
     * @param pageNo    当前第几页
     * @param mContext  上下文Context 对象
     * @param httpListener  接收数据
     * @return
     */
    public static boolean getMoreContacts(String token, int pageNo, Context mContext, HttpListener httpListener){

        JSONObject data = new JSONObject();
        try {
            data.put("token", token);
            data.put("pageNo", pageNo);
            data.put("pageSize", 10);
        }catch (JSONException e){
            e.printStackTrace();
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("pageNo", pageNo);
        map.put("pageSize", 10);

        HttpTask.addHeader("token", "token");


        String url = "";
        //return NetWork.postJson(mContext, url, data.toString(), new HttpResponse(httpListener, url));
        //return NetWork.sendGet(mContext, map, url, new HttpResponse(httpListener, url));
        return NetWork.sendPost(mContext, map, url, new HttpResponse(httpListener, url));
    }
}

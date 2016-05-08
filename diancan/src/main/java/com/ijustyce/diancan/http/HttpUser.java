package com.ijustyce.diancan.http;

import android.content.Context;

import com.ijustyce.fastandroiddev.net.HttpListener;
import com.ijustyce.fastandroiddev.net.HttpParams;
import com.ijustyce.fastandroiddev.net.INetWork;
import com.ijustyce.school2.constant.Constant;

/**
 * Created by yc on 16-4-7. 用户相关的接口
 */
public class HttpUser {

    public static boolean newUser(String tag, String phone, String pw, String name, int shenfen,
                                  Context mContext, HttpListener httpListener){

        HttpParams params = HttpParams.create(tag, HttpConstant.NEW_USER);
        params.add("phone", phone).add("pw", pw).add("name", name).add("appId", Constant.APPID).add("shenfen", shenfen);
        return INetWork.sendGet(mContext, params, httpListener);
    }

    public static boolean login(String tag, String phone, String pw,
                                Context mContext, HttpListener httpListener){

        HttpParams params = HttpParams.create(tag, HttpConstant.LOGIN);
        params.add("phone", phone).add("pw", pw).add("appId", Constant.APPID);
        return INetWork.sendGet(mContext, params, httpListener);
    }

    public static boolean upHead(String tag, String head, Context context, HttpListener listener){

        HttpParams params = HttpParams.create(tag, HttpConstant.UP_HEAD).add("head", head);
        return INetWork.sendGet(context, params, listener);
    }

    public static boolean upPassword(String tag, String phone, String pw, Context context, HttpListener listener){

        HttpParams params = HttpParams.create(tag, HttpConstant.UPDATE_PW).add("phone", phone).add("password", pw);
        return INetWork.sendGet(context, params, listener);
    }
}
